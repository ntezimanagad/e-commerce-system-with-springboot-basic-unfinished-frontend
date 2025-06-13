import React, { useEffect, useState } from "react";
import {
  Box,
  Button,
  Container,
  CssBaseline,
  Drawer,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Typography,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  Paper,
  Pagination,
  Snackbar,
  Alert,
} from "@mui/material";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import ListAltIcon from "@mui/icons-material/ListAlt";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const drawerWidth = 240;

function UserDashboard() {
  const token = localStorage.getItem("token");
  const [products, setProducts] = useState([]);
  const [userId, setUserId] = useState(null);
  const [cartId, setCartId] = useState(null);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const pageSize = 5;
  const navigate = useNavigate();

  useEffect(() => {
    fetchUserId();
  }, []);

  useEffect(() => {
    if (userId !== null) {
      fetchOrCreateCart(userId);
      fetchProducts(page);
    }
  }, [userId, page]);

  const fetchUserId = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/user/getuserid", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setUserId(res.data.id);
    } catch {
      setError("Failed to fetch user ID.");
    }
  };

  const fetchOrCreateCart = async (userId) => {
    try {
      const res = await axios.get("http://localhost:8080/api/cart/get", {
        headers: { Authorization: `Bearer ${token}` },
      });
      const userCart = res.data.find((cart) => cart.user_id === userId);
      if (userCart) {
        setCartId(userCart.id);
      } else {
        const createRes = await axios.post(
          "http://localhost:8080/api/cart/create",
          { user_id: userId },
          { headers: { Authorization: `Bearer ${token}` } }
        );
        setCartId(createRes.data.id);
      }
    } catch {
      setError("Failed to fetch or create cart.");
    }
  };

  const fetchProducts = async (page) => {
    try {
      const res = await axios.get("http://localhost:8080/api/product/pagination", {
        headers: { Authorization: `Bearer ${token}` },
        params: { page, size: pageSize },
      });
      setProducts(res.data.content);
      setTotalPages(res.data.totalPages);
    } catch {
      setError("Failed to fetch products.");
    }
  };

  const addToCart = async (productId) => {
    try {
      await axios.post(
        "http://localhost:8080/api/cartItem/create",
        {
          cart_id: cartId,
          product_id: productId,
          quantity: 1,
        },
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      setSuccess("Added to cart!");
    } catch {
      setError("Failed to add to cart.");
    }
  };

  const handleCheckout = async () => {
    try {
      const res = await axios.post(
        `http://localhost:8080/api/order/checkout?cartId=${cartId}`,
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setSuccess("Checkout successful!");
    } catch {
      setError("Checkout failed.");
    }
  };

  const handlePageChange = (_, value) => {
    setPage(value - 1);
  };

  const sidebarItems = [
    { text: "Cart", icon: <ShoppingCartIcon />, path: "/cart" },
    { text: "Orders", icon: <ListAltIcon />, path: "/order" },
  ];

  return (
    <Box sx={{ display: "flex" }}>
      <CssBaseline />

      {/* Sidebar */}
      <Drawer
        variant="permanent"
        sx={{
          width: drawerWidth,
          [`& .MuiDrawer-paper`]: { width: drawerWidth, boxSizing: "border-box" },
        }}
      >
        <Typography variant="h6" sx={{ m: 2 }}>
          E-Commerce
        </Typography>
        <List>
          {sidebarItems.map((item) => (
            <ListItem key={item.text} disablePadding>
              <ListItemButton onClick={() => navigate(item.path)}>
                <ListItemIcon>{item.icon}</ListItemIcon>
                <ListItemText primary={item.text} />
              </ListItemButton>
            </ListItem>
          ))}
        </List>
      </Drawer>

      {/* Main Content */}
      <Container maxWidth="lg" sx={{ mt: 4, mb: 4, ml: `${drawerWidth}px` }}>
        <Typography variant="h4" gutterBottom>
          Welcome to the Dashboard
        </Typography>

        <Paper sx={{ p: 2 }}>
          <Typography variant="h6" gutterBottom>
            Products
          </Typography>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Name</TableCell>
                <TableCell>Description</TableCell>
                <TableCell>Price</TableCell>
                <TableCell>Stock</TableCell>
                <TableCell>Image</TableCell>
                <TableCell align="center">Action</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {products.length > 0 ? (
                products.map((product) => (
                  <TableRow key={product.id}>
                    <TableCell>{product.name}</TableCell>
                    <TableCell>{product.descrption}</TableCell>
                    <TableCell>${product.price}</TableCell>
                    <TableCell>{product.stock}</TableCell>
                    <TableCell>
                      {product.imageUrl ? (
                        <img src={product.imageUrl} alt={product.name} width="60" />
                      ) : (
                        "No image"
                      )}
                    </TableCell>
                    <TableCell align="center">
                      <Button
                        variant="contained"
                        color="primary"
                        onClick={() => addToCart(product.id)}
                      >
                        Add to Cart
                      </Button>
                    </TableCell>
                  </TableRow>
                ))
              ) : (
                <TableRow>
                  <TableCell colSpan={6} align="center">
                    No products found.
                  </TableCell>
                </TableRow>
              )}
            </TableBody>
          </Table>

          <Box display="flex" justifyContent="center" my={2}>
            <Pagination
              count={totalPages}
              page={page + 1}
              onChange={handlePageChange}
              color="primary"
            />
          </Box>

          <Box textAlign="center" mt={4}>
            <Button
              variant="contained"
              color="success"
              onClick={handleCheckout}
              disabled={!cartId}
            >
              Proceed to Checkout
            </Button>
          </Box>
        </Paper>

        {/* Snackbars */}
        <Snackbar
          open={!!success}
          autoHideDuration={3000}
          onClose={() => setSuccess("")}
        >
          <Alert onClose={() => setSuccess("")} severity="success" sx={{ width: "100%" }}>
            {success}
          </Alert>
        </Snackbar>

        <Snackbar
          open={!!error}
          autoHideDuration={3000}
          onClose={() => setError("")}
        >
          <Alert onClose={() => setError("")} severity="error" sx={{ width: "100%" }}>
            {error}
          </Alert>
        </Snackbar>
      </Container>
    </Box>
  );
}

export default UserDashboard;