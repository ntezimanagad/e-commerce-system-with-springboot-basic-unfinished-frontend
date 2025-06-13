import React, { useEffect, useState } from "react";
import axios from "axios";

function AdminProductPage() {
  const token = localStorage.getItem("token");
  const [products, setProducts] = useState([]);
  const [form, setForm] = useState({
    name: "",
    descrption: "",
    price: "",
    stock: "",
    imageUrl: ""
  });
  const [editingId, setEditingId] = useState(null);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const fetchProducts = async () => {
    try {
      const res = await axios.get(`http://localhost:8080/api/product/pagination`, {
        params: { page, size: 5 },
        headers: { Authorization: `Bearer ${token}` }
      });
      setProducts(res.data.content);
      setTotalPages(res.data.totalPages);
    } catch (err) {
      console.error("Failed to fetch products", err);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, [page]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleCreate = async (e) => {
    e.preventDefault();
    try {
      await axios.post("http://localhost:8080/api/product/create", form, {
        headers: { Authorization: `Bearer ${token}` }
      });
      resetForm();
      fetchProducts();
    } catch (err) {
      console.error("Error creating product", err);
    }
  };

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      await axios.put(`http://localhost:8080/api/product/update/${editingId}`, form, {
        headers: { Authorization: `Bearer ${token}` }
      });
      resetForm();
      fetchProducts();
    } catch (err) {
      console.error("Error updating product", err);
    }
  };

  const handleEdit = (product) => {
    setForm(product);
    setEditingId(product.id);
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure to delete this product?")) return;
    try {
      await axios.delete(`http://localhost:8080/api/product/delete/${id}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      fetchProducts();
    } catch (err) {
      console.error("Failed to delete", err);
    }
  };

  const resetForm = () => {
    setForm({ name: "", descrption: "", price: "", stock: "", imageUrl: "" });
    setEditingId(null);
  };

  return (
    <div>
      <h2>Admin Product Management</h2>

      <form onSubmit={editingId ? handleUpdate : handleCreate}>
        <input type="text" name="name" placeholder="Name" value={form.name} onChange={handleChange} required />
        <input type="text" name="descrption" placeholder="Description" value={form.descrption} onChange={handleChange} required />
        <input type="number" name="price" placeholder="Price" value={form.price} onChange={handleChange} required />
        <input type="number" name="stock" placeholder="Stock" value={form.stock} onChange={handleChange} required />
        <input type="text" name="imageUrl" placeholder="Image URL" value={form.imageUrl} onChange={handleChange} />
        <button type="submit">{editingId ? "Update Product" : "Create Product"}</button>
        {editingId && <button type="button" onClick={resetForm}>Cancel</button>}
      </form>

      <table border="1" cellPadding="8" style={{ marginTop: "20px", width: "100%" }}>
        <thead>
          <tr>
            <th>Name</th><th>Description</th><th>Price</th><th>Stock</th><th>Image</th><th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {products.map((p) => (
            <tr key={p.id}>
              <td>{p.name}</td>
              <td>{p.descrption}</td>
              <td>${p.price}</td>
              <td>{p.stock}</td>
              <td>{p.imageUrl ? <img src={p.imageUrl} alt={p.name} width="50" /> : "N/A"}</td>
              <td>
                <button onClick={() => handleEdit(p)}>Edit</button>
                <button onClick={() => handleDelete(p.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <div style={{ marginTop: "20px" }}>
        <button onClick={() => setPage(page - 1)} disabled={page <= 0}>Prev</button>
        <span> Page {page + 1} of {totalPages} </span>
        <button onClick={() => setPage(page + 1)} disabled={page + 1 >= totalPages}>Next</button>
      </div>
    </div>
  );
}

export default AdminProductPage;
