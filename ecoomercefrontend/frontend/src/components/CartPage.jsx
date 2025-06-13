import React, { useEffect, useState } from "react";
import axios from "axios";

function CartPage() {
  const token = localStorage.getItem("token");
  const [cart, setCart] = useState(null);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  useEffect(() => {
    fetchCart();
  }, []);

  const fetchCart = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/cartItem/get", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setCart(res.data);
    } catch (err) {
      setError("Failed to fetch cart.");
    }
  };

  const removeItem = async (itemId) => {
    try {
      await axios.delete(`http://localhost:8080/api/cartItem/delete/${itemId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setSuccess("Item removed.");
      fetchCart();
    } catch {
      setError("Failed to remove item.");
    }
  };

  const totalPrice = cart?.cartItems?.reduce(
    (acc, item) => acc + item.quantity * item.product?.price,
    0
  );

  return (
    <div>
      <h2>Your Cart</h2>
      {error && <p style={{ color: "red" }}>{error}</p>}
      {success && <p style={{ color: "green" }}>{success}</p>}


        <table border="1">
          <thead>
            <tr>
              <th>Product</th>
              <th>Qty</th>
              <th>Product Id</th>
              <th>Remove</th>
            </tr>
          </thead>
          <tbody>
            {Array.isArray(cart) && cart.map((item) => (
              <tr key={item.id}>
                <td>{item.product?.name}</td>
                <td>{item.quantity}</td>
                <td>{item.product_id}</td>
                <td>
                  <button onClick={() => removeItem(item.id)}>Remove</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      

      <h3>Total: ${totalPrice?.toFixed(2)}</h3>
    </div>
  );
}

export default CartPage;
