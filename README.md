# 🚀 Sistema de Reportes Financieros (Enterprise Edition)

Backend robusto desarrollado con **Java 17** y **Spring Boot 3**, diseñado para procesar grandes volúmenes de datos de forma asíncrona utilizando una arquitectura desacoplada en AWS.

## 🏗️ Arquitectura

El sistema sigue una arquitectura por capas (Layered Architecture) y utiliza patrones de diseño modernos para garantizar escalabilidad y resiliencia.

* **API REST:** Spring Boot Web (Stateless).
* **Seguridad:** Spring Security (Basic Auth / Prepared for JWT).
* **Base de Datos:** PostgreSQL (Prod) / H2 In-Memory (Dev/Test).
* **Procesamiento Asíncrono:** Spring Async (`@Async`) con Thread Pools gestionados.
* **Almacenamiento Cloud:** AWS S3 (Streaming directo en memoria para evitar I/O en disco).
* **Infraestructura:** Terraform (IaC).

---

## 🛠️ Tecnologías

* **Lenguaje:** Java 17 (LTS)
* **Framework:** Spring Boot 3.2
* **Build Tool:** Maven
* **Cloud:** AWS (S3, EC2, IAM)
* **Containerization:** Docker & Docker Compose
* **IaC:** Terraform

---

## ⚙️ Configuración Local (Quick Start)

Para ejecutar el proyecto en tu máquina sin instalar bases de datos externas:

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/tu-usuario/reportes-backend.git](https://github.com/tu-usuario/reportes-backend.git)
    cd reportes-backend
    ```

2.  **Ejecutar en modo "Local" (H2 Database):**
    Este perfil levanta una base de datos en memoria y simula la conexión a AWS.
    ```bash
    mvn spring-boot:run -Dspring-boot.run.profiles=local
    ```

3.  **Probar el Endpoint:**
    ```bash
    curl -u admin:password123 -X POST http://localhost:8080/api/v1/reportes \
    -H "Content-Type: application/json" \
    -d '{ "usuarioId": 1, "tipoReporte": "FINANCIERO" }'
    ```

---

## 🧪 Testing

El proyecto cuenta con una estrategia de pruebas separada por perfiles Maven:

* **Unit Tests:** Se ejecutan por defecto.
    ```bash
    mvn test
    ```
* **Integration Tests:** Pruebas de flujo completo (Controller -> Service -> Mock S3).
    ```bash
    mvn test -P integration-test
    ```

---

## ☁️ Infraestructura (Despliegue AWS)

La infraestructura se gestiona con Terraform.

### Prerrequisitos
* AWS CLI configurado (`aws configure`).
* Terraform instalado.

### Pasos de Despliegue
1.  Navegar a la carpeta de infraestructura:
    ```bash
    cd terraform
    ```
2.  Inicializar y planificar:
    ```bash
    terraform init
    terraform plan
    ```
3.  Aplicar cambios (Creará EC2, S3, Security Groups y Roles IAM):
    ```bash
    terraform apply -auto-approve
    ```
4.  **Resultado:** Obtendrás la IP pública del servidor para configurar tu CI/CD.

---

## 🔒 Seguridad
* Las credenciales de AWS **NO** están en el código; se usan **IAM Roles**.
* Las contraseñas de BD se inyectan vía Variables de Entorno en Producción.