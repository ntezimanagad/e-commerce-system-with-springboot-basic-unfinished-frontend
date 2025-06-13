import React from 'react'
import {jwtDecode} from "jwt-decode";
import { Navigate } from "react-router-dom";

function PrivateRoutes({role, children}) {
  const token = localStorage.getItem("token")
  if (!token) return <Navigate to="/login"/>

  try {
    const now = Date.now() / 1000
    const decode = jwtDecode(token);

    if (decode.exp && decode.exp < now) {
        localStorage.removeItem("token")
        return <Navigate to="/login"/>
    }

    const roles = decode.role ? [decode.role] : []

    if (role && !roles.includes(role)) {
        localStorage.removeItem("token")
        return <Navigate to="/login"/>
    }
    return children
  } catch (error) {
    console.log("invalid token")
    localStorage.removeItem("token")
    return <Navigate to="/login"/>
  }
}

export default PrivateRoutes