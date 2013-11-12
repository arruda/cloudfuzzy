
class java (
  $java_package_name = "sun-java6-jdk",
) {

    exec { 'wget_oab_java':
        cwd     =>'/tmp/',
        command => 'wget https://github.com/flexiondotorg/oab-java6/raw/0.3.0/oab-java.sh -O oab-java.sh',
        unless    => "dpkg -l ${java_package_name} | grep -e '^ii'",
        logoutput => false,
        # require => Exec["south_migrate"]
    }
    exec { 'chmod_x_oab_java':
        cwd     =>'/tmp/',
        command => 'chmod +x oab-java.sh',
        unless    => "dpkg -l ${java_package_name} | grep -e '^ii'",
        logoutput => false,
        require => Exec["wget_oab_java"]
    }

    exec { 'install_pkg_oab_java':
        cwd     =>'/tmp/',
        command => "echo -ne '\n' | /tmp/oab-java.sh",
        unless    => "dpkg -l ${java_package_name} | grep -e '^ii'",
        logoutput => true,
        require => Exec["chmod_x_oab_java"]
    }

    package { [
        "${java_package_name}"
      ]:
      ensure  => 'installed',
      require => Exec["install_pkg_oab_java"]
    }

}
