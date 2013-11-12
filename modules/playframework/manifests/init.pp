class playframework (
  $version = "2.0.2",
) {

    exec { 'wget_playframework':
        cwd     =>'/home/vagrant/',
        command => "wget http://downloads.typesafe.com/releases/play-${version}.zip -O play-${version}.zip",
        unless    => "ls -l /home/vagrant/ | grep -e 'play-${version}.zip'",
        logoutput => false,
    }
    exec { 'unzip_playframework':
        cwd     =>'/home/vagrant/',
        command => "unzip /home/vagrant/play-${version}.zip -d /home/vagrant",
        unless    => "ls -l /home/vagrant/ | grep -v 'play-${version}.zip' |grep -e 'play-${version}'",
        logoutput => false,
        require => Exec["wget_playframework"]
    }
    exec { 'chown_playframework':
        cwd     =>'/home/vagrant/',
        command => "chown -R vagrant:vagrant /home/vagrant/play-${version}/",
        onlyif => "ls -l /home/vagrant/ | grep -v 'play-${version}.zip' |grep -e 'play-${version}'",
        logoutput => false,
        require => Exec["unzip_playframework"]
    }
    file { "/home/vagrant/bin/":
        path    => "/home/vagrant/bin/",
        ensure => 'directory',
        owner  => "vagrant",
        group  => "vagrant",
        require => Exec["chown_playframework"],
    }
    file { "/home/vagrant/bin/play":
        ensure => 'link',
        target => "/home/vagrant/play-${version}/play",
        require => File["/home/vagrant/bin/"],
    }

}
