# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure("2") do |config|
  config.vm.box = "precise64.box"
  config.vm.box_url = "http://files.vagrantup.com/precise64.box"


  config.vm.network :private_network, ip: "192.168.56.101"
  config.vm.network :forwarded_port, guest: 9000, host: 9000


  config.vm.provider :virtualbox do |vb|
    vb.customize ["modifyvm", :id, "--natdnshostresolver1", "on"]
    vb.customize ["modifyvm", :id, "--memory", 1024]
    vb.customize ["modifyvm", :id, "--name", "cloudfuzzy-vm"]
  end

  # Para instalar esse pluging no vagrant, rode o comando:
  # $ vagrant plugin install vagrant-vbguest
  config.vbguest.auto_update = false

  # ensure that it is apt-get updated before puppet,
  # had to put this to puppet find the correct dns
  config.vm.provision :shell, :inline =>
    "if [[ ! -f /apt-get-run ]]; then sudo apt-get update && sudo touch /apt-get-run; fi"

  config.vm.provision :puppet do |puppet|
    puppet.manifests_path = "manifests"
    puppet.manifest_file  = "cf.pp"
    puppet.module_path = "./modules"
    puppet.options = "--verbose"
  end
end
