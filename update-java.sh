#!/bin/bash

# Atualiza os repositórios de pacotes
sudo apt-get update

# Desinstala o Java 11
echo "Desinstalando o Java 11..."
sudo apt-get remove --purge openjdk-11-*

# Instala o Java 17
echo "Instalando o Java 17..."
sudo apt-get install openjdk-17-jdk

# Configura o Java 17 como padrão
echo "Configurando o Java 17 como padrão..."
sudo update-alternatives --config java

# Define JAVA_HOME para o Java 17
JAVA_HOME=$(update-alternatives --query java | grep 'Value: ' | grep java-17 | sed 's/Value: //')
echo "export JAVA_HOME=$JAVA_HOME" >> ~/.bashrc
echo "export PATH=\$JAVA_HOME/bin:\$PATH" >> ~/.bashrc

# Recarrega o arquivo .bashrc
source ~/.bashrc

echo "Java 17 instalado e configurado com sucesso."
