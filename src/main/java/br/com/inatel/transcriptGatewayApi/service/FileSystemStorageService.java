package br.com.inatel.transcriptGatewayApi.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.inatel.transcriptGatewayApi.exception.StorageException;
import br.com.inatel.transcriptGatewayApi.exception.StorageFileNotFoundException;
import br.com.inatel.transcriptGatewayApi.handler.ExceptionsMessage;
import br.com.inatel.transcriptGatewayApi.properties.StorageProperties;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				log.error(ExceptionsMessage.FILE_EMPTY_ERROR);
				throw new StorageException(ExceptionsMessage.FILE_EMPTY_ERROR);
			}
			Path destinationFile = this.rootLocation.resolve(
					Paths.get(file.getOriginalFilename()))
					.normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				// This is a security check
				log.error(ExceptionsMessage.FILE_STORAGE_OUTSIDE_ERROR);
				throw new StorageException(ExceptionsMessage.FILE_STORAGE_OUTSIDE_ERROR);
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile,
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			log.error(ExceptionsMessage.FILE_STORAGE_OUTSIDE_ERROR, e);
			throw new StorageException(ExceptionsMessage.FILE_STORAGE_ERROR);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
				.filter(path -> !path.equals(this.rootLocation))
				.map(this.rootLocation::relativize);
		}
		catch (IOException e) {
			log.error(ExceptionsMessage.FILE_READ_ERROR, e);
			throw new StorageException(ExceptionsMessage.FILE_READ_ERROR);
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
			log.error(ExceptionsMessage.FILE_READ_ERROR);
				throw new StorageFileNotFoundException(
					ExceptionsMessage.FILE_READ_ERROR + filename);

			}
		}
		catch (MalformedURLException e) {
			log.error(ExceptionsMessage.FILE_READ_ERROR, e);
			throw new StorageFileNotFoundException(ExceptionsMessage.FILE_READ_ERROR + filename);
		}
	}

	@Override
	public void deleteAll() {
		try {
			FileSystemUtils.deleteRecursively(rootLocation.toFile());
			
		} catch (Exception e) {
			log.error(ExceptionsMessage.FILE_DELETE_ERROR, e);
			throw new StorageFileNotFoundException(ExceptionsMessage.FILE_DELETE_ERROR);
		}
	}

	@Override
	public void deleteOne(String filename) {

		try {
			Path toDelete = Paths.get(rootLocation.toFile().getName(), filename);
			toDelete.toFile().delete();
			
		} catch (Exception e) {
			log.error(ExceptionsMessage.FILE_DELETE_ERROR, e);
			throw new StorageFileNotFoundException(ExceptionsMessage.FILE_DELETE_ERROR + filename);
		}
		
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			log.error(ExceptionsMessage.STORAGE_INITIALIZE_ERROR);
			throw new StorageException(ExceptionsMessage.STORAGE_INITIALIZE_ERROR, e);
		}
	}
}
