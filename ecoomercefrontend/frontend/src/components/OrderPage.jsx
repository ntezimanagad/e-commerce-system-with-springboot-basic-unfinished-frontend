// import React, { useEffect, useState } from "react";
// import axios from "axios";

// function OrderPage() {
//   const token = localStorage.getItem("token");
//   const [orders, setOrders] = useState([]);
//   const [error, setError] = useState("");
//   const [orderItemsMap, setOrderItemsMap] = useState({});

//   useEffect(() => {
//     fetchOrders();
//   }, []);

//   const fetchOrders = async () => {
//   try {
//     const res = await axios.get("http://localhost:8080/api/order/get", {
//       headers: { Authorization: `Bearer ${token}` },
//     });
//     setOrders(res.data);

//     // Now fetch order items for each order
//     const itemsMap = {};
//     for (const order of res.data) {
//       const itemRes = await axios.get(`http://localhost:8080/api/OrderItem/get`, {
//         headers: { Authorization: `Bearer ${token}` },
//       });
//       itemsMap[order.id] = itemRes.data;
//     }
//     setOrderItemsMap(itemsMap);
//   } catch (err) {
//     setError("Failed to fetch orders.");
//   }
// };

//   return (
//     <div>
//       <h2>Your Orders</h2>
//       {error && <p style={{ color: "red" }}>{error}</p>}
//       {orders.length === 0 ? (
//         <p>No orders yet.</p>
//       ) : (
//         orders.map((order) => (
//   <div key={order.id}>
//     <h3>Order #{order.id}</h3>
//     <p>Date: {new Date(order.date).toLocaleString()}</p>
//     <ul>
//       {(orderItemsMap[order.id] || []).map((item) => (
//         <li key={item.id}>
//           {item.product?.name || `Product ID: ${item.product_id}`} - Qty: {item.quantity} - $
//           {item.product?.price || item.priceAtPurchase}
//         </li>
//       ))}
//     </ul>
//     <hr />
//   </div>
// ))

//       )}
//     </div>
//   );
// }

// export default OrderPage;
import React, { useEffect, useState } from "react";
import {
  Box,
  Button,
  Card,
  CardContent,
  Typography,
  List,
  ListItem,
  ListItemText,
  CircularProgress,
  Alert,
  Snackbar,
} from "@mui/material";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function OrderPage() {
  const token = localStorage.getItem("token");
  const [orders, setOrders] = useState([]);
  const [orderItemsMap, setOrderItemsMap] = useState({});
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    fetchOrders();
  }, []);

  const fetchOrders = async () => {
    setLoading(true);
    try {
      const res = await axios.get("http://localhost:8080/api/order/get", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setOrders(res.data);

      const itemsMap = {};
      for (const order of res.data) {
        const itemRes = await axios.get(`http://localhost:8080/api/OrderItem/get`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        itemsMap[order.id] = itemRes.data;
      }
      setOrderItemsMap(itemsMap);
    } catch (err) {
      setError("Failed to fetch orders or order items.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box sx={{ p: 4 }}>
      {/* Header + Back Button */}
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4">Your Orders</Typography>
        <Button
          variant="outlined"
          startIcon={<ArrowBackIcon />}
          onClick={() => navigate("/user")}
        >
          Back to Dashboard
        </Button>
      </Box>

      {/* Content */}
      {loading ? (
        <Box display="flex" justifyContent="center" mt={5}>
          <CircularProgress />
        </Box>
      ) : orders.length === 0 ? (
        <Typography>No orders placed yet.</Typography>
      ) : (
        orders.map((order) => (
          <Card key={order.id} sx={{ mb: 3, boxShadow: 3 }}>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Order #{order.id}
              </Typography>
              <Typography color="text.secondary" gutterBottom>
                Date: {order.date ? new Date(order.date).toLocaleString() : "Unknown"}
              </Typography>
              <List>
                {(orderItemsMap[order.id] || []).map((item) => (
                  <ListItem key={item.id} disablePadding>
                    <ListItemText
                      primary={`${item.product?.name || `Product ID: ${item.product_id}`}`}
                      secondary={`Quantity: ${item.quantity ?? "?"} | Price: $${item.product?.price ?? item.priceAtPurchase ?? "?"}`}
                    />
                  </ListItem>
                ))}
              </List>
            </CardContent>
          </Card>
        ))
      )}

      {/* Error Snackbar */}
      <Snackbar open={!!error} autoHideDuration={4000} onClose={() => setError("")}>
        <Alert severity="error" onClose={() => setError("")}>
          {error}
        </Alert>
      </Snackbar>
    </Box>
  );
}

export default OrderPage;

