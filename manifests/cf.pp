group { 'puppet': ensure => present }
Exec { path => [ '/bin/', '/sbin/', '/usr/bin/', '/usr/sbin/'] }
File { owner => 0, group => 0, mode => 0644 }

class {'apt':
  always_apt_update => true,
}

package { [
    'build-essential',
    'vim',
    'curl',
    'git-core'
  ]:
  ensure  => 'installed',
}->

class { 'java':
} ->
class { 'playframework':
    version => "2.0.2",
}

->
exec { "add_cf_env_vars_CF_XFL_PATH":
    command => "/bin/echo 'export CF_XFL_PATH=/vagrant/extraFiles/xfls/' >> /home/vagrant/.bashrc",
    unless    => "cat /home/vagrant/.bashrc | grep -e 'CF_XFL_PATH'",
}

->
exec { "add_cf_env_vars_CF_PKG_PATH":
    command => "/bin/echo 'export CF_PKG_PATH=/vagrant/extraFiles/' >> /home/vagrant/.bashrc",
    unless    => "cat /home/vagrant/.bashrc | grep -e 'CF_PKG_PATH'",
}
