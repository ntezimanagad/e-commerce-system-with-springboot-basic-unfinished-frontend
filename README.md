# 🛒 E-Commerce App (Spring Boot + React)

This is a simple full-stack E-Commerce application built with:

- **Backend:** Spring Boot (Java)
- **Frontend:** React.js
- **Database:** MySQL
- **Authentication:** JWT (JSON Web Token)

## 📚 Features

### 🧑‍💼 Admin
- Add / Update / Delete products
- Paginated product management

### 🧑‍💻 User
- View paginated products
- Add to cart
- Checkout
- View order history


## 🚀 Getting Started

### 🔧 Backend Setup (Spring Boot)

1. Clone the repository and open the `backend` folder in your IDE.
2. Configure `application.properties` for your local MySQL DB:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
    spring.datasource.username=root
    spring.datasource.password=yourpassword
    spring.jpa.hibernate.ddl-auto=update
    jwt.secret=your_jwt_secret
    ```
3. Run the application with `mvn spring-boot:run`.

### 🌐 Frontend Setup (React)

1. Navigate to the `frontend` directory:
    ```bash
    cd frontend
    ```
2. Install dependencies:
    ```bash
    npm install
    ```
3. Start the development server:
    ```bash
    npm start
    ```

## 🔐 Authentication

- JWT tokens are stored in `localStorage`.
- Protected endpoints require `Authorization: Bearer <token>` headers.

## 🧪 API Endpoints

### Product
- `GET /api/product/getAll`
- `POST /api/product/add`
- `PUT /api/product/update/{id}`
- `DELETE /api/product/delete/{id}`

### Cart
- `POST /api/cart/add`
- `GET /api/cart/user/{userId}`
- `POST /api/cart/checkout` → creates an order

### Order
- `GET /api/order/get` → Get all orders for current user
- `GET /api/OrderItem/getByOrderId/{orderId}`

## 📦 Tech Stack

| Technology    | Role               |
|---------------|--------------------|
| Spring Boot   | Backend API        |
| React         | Frontend UI        |
| MySQL         | Database           |
| JWT           | Auth & Authorization |
| Axios         | HTTP Client        |
| MUI           | UI Components      |

## 📌 TODO

- ✅ Admin dashboard
- ✅ JWT login
- ✅ Checkout flow
- 🕓 Add product images
- 🕓 Payment integration
- 🕓 Unit and integration testing

## 🧑‍💻 Author

Developed by [Your Name].

---

> Feel free to fork this project and extend it!


