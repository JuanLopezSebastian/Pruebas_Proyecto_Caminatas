pipeline {
   agent any
   environment {
      GIT_REPO = 'Pruebas_Proyecto_Caminatas'
      GIT_CREDENTIAL_ID = 'Github-token' // Actualiza esto con el ID correcto de las credenciales de Git en Jenkins
      SONARQUBE_URL = 'http://sonarqube:9004' // URL de tu SonarQube
      SONAR_TOKEN = credentials('Sonar-token') // Asegúrate de que este ID coincida con el token almacenado en Jenkins
   }
   stages {
      stage('Checkout') {
         steps {
            scmSkip(deleteBuild: true, skipPattern:'.*\\[ci-skip\\].*') // Esta es una función extra que puedes usar para omitir el build si hay un [ci-skip] en el commit message

            // Checkout del repositorio
            git branch: 'main',
               credentialsId: env.GIT_CREDENTIAL_ID,
               url: 'https://github.com/JuanLopezSebastian/' + env.GIT_REPO + '.git'
         }
      }
      stage('Build') {
         steps {
             script {
               try {
                  docker.image('maven:3.9.4-eclipse-temurin-21').inside('-v $HOME/.m2:/root/.m2:ro') {
                     sh 'mvn clean install -Dmaven.repo.local=/var/jenkins_home/.m2/repository'
                  }
               } catch (Exception e) {
                  error("Error durante el build: ${e.message}")
               }
            }
         }
      }
      stage('Testing') {
         steps {
            script {
               // Ejecutar pruebas unitarias dentro de un contenedor Docker
               docker.image('maven:3.9.4-eclipse-temurin-21').inside('-v $HOME/.m2:/root/.m2:z')  {
                  sh '''
                     mvn test
                  '''
               }
            }
         }
      }
      stage('Static Analysis') {
         steps {
            script {
               // Ejecutar análisis estático con SonarQube
               docker.image('maven:3.9.4-eclipse-temurin-21').inside('-v $HOME/.m2:/root/.m2:z')  {
                  sh '''
                     mvn sonar:sonar -Dsonar.token=${SONAR_TOKEN} -Dsonar.host.url=${SONARQUBE_URL}
                  '''
               }
            }
         }
      }
   }
   post {
      always {
         cleanWs() // Limpia el espacio de trabajo después del build
         deleteDir() // Elimina los archivos temporales
         dir("${env.GIT_REPO}@tmp") {
            deleteDir() // Elimina directorios temporales relacionados con el repo
         }
      }
   }
}
