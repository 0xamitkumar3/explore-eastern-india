
import { Link } from "react-router-dom";

function Footer() {
  return (
    <footer className="bg-gray-950 text-gray-300">

      <div className="mx-auto grid max-w-7xl grid-cols-1 gap-10 px-6 py-14 sm:grid-cols-2 lg:grid-cols-4">

        {/* Brand */}
        <div>
          <h2 className="text-2xl font-bold text-white">
            Explore<span className="text-orange-500">Eastern</span>
          </h2>

          <p className="mt-4 text-sm leading-7 text-gray-400">
            Discover the rich culture, heritage, traditions and
            beautiful destinations of Eastern India.
          </p>
        </div>

        {/* Quick Links */}
        <div>
          <h3 className="mb-5 text-lg font-semibold text-white">
            Quick Links
          </h3>

          <ul className="space-y-3 text-sm">
            <li><Link to="/" className="hover:text-orange-400">Home</Link></li>
            <li><Link to="/destinations" className="hover:text-orange-400">Destinations</Link></li>
            <li><Link to="/services" className="hover:text-orange-400">Services</Link></li>
            <li><Link to="/guides" className="hover:text-orange-400">Local Guides</Link></li>
          </ul>
        </div>

        {/* Popular Destinations */}
        <div>
          <h3 className="mb-5 text-lg font-semibold text-white">
            Explore Bihar
          </h3>

          <ul className="space-y-3 text-sm">
            <li>Bodh Gaya</li>
            <li>Nalanda</li>
            <li>Rajgir</li>
            <li>Vaishali</li>
            <li>Pawapuri</li>
          </ul>
        </div>

        {/* Contact */}
        <div>
          <h3 className="mb-5 text-lg font-semibold text-white">
            Contact Us
          </h3>

          <ul className="space-y-3 text-sm text-gray-400">
            <li>Eastern India, India</li>
            <li>Explore our cultural heritage</li>
            <li>Travel. Discover. Experience.</li>
          </ul>
        </div>

      </div>

      {/* Copyright */}
      <div className="border-t border-gray-800">
        <div className="mx-auto max-w-7xl px-6 py-6 text-center text-sm text-gray-500">
          © {new Date().getFullYear()} Explore Eastern India.
          All rights reserved.
        </div>
      </div>

    </footer>
  );
}

export default Footer;
