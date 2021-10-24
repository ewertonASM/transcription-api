LINUX_PACKAGES="ffmpeg"

function install_system_dependencies {
  echo "Installing required system dependencies..."
  (apt --assume-yes install $LINUX_PACKAGES) || return 1
  return 0
}

install_system_dependencies &&\

echo -e "\nSuccessful installation!" || echo -e "\nInstallation failed!"
