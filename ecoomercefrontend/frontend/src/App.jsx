import { BrowserRouter as Router, Routes, Route } from "react-router-dom"
import Register from "./components/Register"
import Login from "./components/Login"
import UserDashboard from "./components/UserDashboard"
import PrivateRoutes from "./components/PrivateRoutes"
import AdminProductPage from "./components/AdminProductPage"
import OrderPage from "./components/OrderPage"
import CartPage from "./components/CartPage"

function App() {

  return (
    <>
      <Router>
        <Routes>
          <Route path="/register" element={<Register/>}/>
          <Route path="/login" element={<Login/>}/>
          <Route path="/user" element={<PrivateRoutes role="USER"><UserDashboard/></PrivateRoutes>}/>
          <Route path="/order" element={<PrivateRoutes role="USER"><OrderPage/></PrivateRoutes>}/>
          <Route path="/cart" element={<PrivateRoutes role="USER"><CartPage/></PrivateRoutes>}/>
          <Route path="/admin" element={<PrivateRoutes role="ADMIN"><AdminProductPage/></PrivateRoutes>}/>
        </Routes>
      </Router>
    </>
  )
}

export default App
