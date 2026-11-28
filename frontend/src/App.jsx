
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Layout from "./components/layout/Layout";

import Home from "./pages/Home";
import Destinations from "./pages/Destinations";
import DestinationDetails from "./pages/DestinationDetails";
import Services from "./pages/Services";
import Guides from "./pages/Guides";
import Login from "./pages/Login";
import AdminDashboard from "./pages/AdminDashboard";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Public Website Layout */}
        <Route element={<Layout />}>
          <Route path="/" element={<Home />} />
          <Route path="/destinations" element={<Destinations />} />
          <Route
            path="/destinations/:id"
            element={<DestinationDetails />}
          />
          <Route path="/services" element={<Services />} />
          <Route path="/guides" element={<Guides />} />
        </Route>

        {/* Admin Pages - Without Public Navbar and Footer */}
        <Route path="/login" element={<Login />} />
        <Route path="/admin" element={<AdminDashboard />} />

        {/* 404 Page */}
        <Route
          path="*"
          element={
            <h1 className="p-10 text-center text-2xl font-bold">
              404 - Page Not Found
            </h1>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
