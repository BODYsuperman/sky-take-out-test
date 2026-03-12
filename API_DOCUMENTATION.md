# Sky Takeout API Documentation

## Base URL
`http://localhost:8080`

## Authentication
- JWT Token required for all endpoints except user login
- Token should be included in Authorization header: `Bearer <token>`

## API Endpoints

### 1. Employee Management
- `POST /employee/login` - Employee login
- `POST /employee/logout` - Employee logout  
- `GET /employee` - Get employee info
- `PUT /employee` - Update employee info
- `GET /employee/page` - Get employee list with pagination
- `POST /employee` - Create new employee
- `DELETE /employee` - Delete employee
- `PUT /employee/status/{status}` - Update employee status

### 2. Category Management
- `GET /category/list` - Get category list
- `POST /category` - Create category
- `DELETE /category` - Delete category
- `PUT /category` - Update category

### 3. Dish Management
- `GET /dish/list` - Get dish list with pagination
- `POST /dish` - Create dish
- `DELETE /dish` - Delete dish
- `PUT /dish` - Update dish
- `GET /dish/{id}` - Get dish by ID

### 4. Setmeal Management
- `GET /setmeal/list` - Get setmeal list with pagination
- `POST /setmeal` - Create setmeal
- `DELETE /setmeal` - Delete setmeal
- `PUT /setmeal` - Update setmeal
- `GET /setmeal/{id}` - Get setmeal by ID
- `POST /setmeal/status/{status}` - Update setmeal status

### 5. Shopping Cart
- `POST /shoppingCart/add` - Add item to cart
- `GET /shoppingCart/list` - Get shopping cart items
- `DELETE /shoppingCart/clean` - Clear shopping cart
- `DELETE /shoppingCart` - Delete specific cart item

### 6. Address Book
- `GET /addressBook/list` - Get address list
- `POST /addressBook` - Create address
- `PUT /addressBook` - Update address
- `DELETE /addressBook` - Delete address
- `PUT /addressBook/default` - Set default address

### 7. Orders Management
- `POST /order/submit` - Submit order
- `GET /order/userPage` - Get user orders with pagination
- `GET /order/details/{id}` - Get order details
- `PUT /order/status/{status}` - Update order status (admin)

### 8. User Management
- `POST /user/sendMsg` - Send verification code
- `POST /user/login` - User login with WeChat
- `GET /user/{id}` - Get user info

## Error Codes
- `401`: Unauthorized
- `403`: Forbidden
- `404`: Not Found
- `500`: Internal Server Error

## Response Format
```json
{
  "code": 200,
  "msg": "success",
  "data": {}
}
```