
import { useEffect, useMemo, useState } from "react";

import { getAllServices } from "../services/serviceService";
import ServiceCard from "../components/services/ServiceCard";

function Services() {
  const [services, setServices] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [search, setSearch] = useState("");
  const [selectedCategory, setSelectedCategory] = useState("ALL");

  const fetchServices = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await getAllServices();
      setServices(data);
    } catch (err) {
      console.error("Error fetching tourism services:", err);
      setError("Unable to load tourism services. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchServices();
  }, []);

  const categories = [
    "ALL",
    "HOTEL",
    "RESTAURANT",
    "TRANSPORT",
    "TOUR_OPERATOR",
  ];

  const filteredServices = useMemo(() => {
    return services.filter((service) => {
      const matchesCategory =
        selectedCategory === "ALL" ||
        service.category === selectedCategory;

      const searchText = search.toLowerCase();

      const matchesSearch =
        service.name?.toLowerCase().includes(searchText) ||
        service.description?.toLowerCase().includes(searchText) ||
        service.district?.toLowerCase().includes(searchText);

      return matchesCategory && matchesSearch;
    });
  }, [services, search, selectedCategory]);

  return (
    <main className="min-h-screen bg-gray-50">

      {/* Hero Section */}
      <section className="bg-gradient-to-br from-emerald-950 via-emerald-800 to-teal-700 px-6 py-20 text-center">

        <p className="text-sm font-bold uppercase tracking-[0.25em] text-orange-300">
          Explore Eastern India
        </p>

        <h1 className="mt-4 text-4xl font-extrabold text-white md:text-5xl">
          Tourism Services
        </h1>

        <p className="mx-auto mt-5 max-w-2xl text-base leading-7 text-emerald-50 md:text-lg">
          Discover hotels, restaurants, transportation and local tour
          operators to make your journey comfortable and memorable.
        </p>

        <div className="mx-auto mt-8 h-1 w-20 rounded-full bg-orange-400" />
      </section>

      {/* Services Section */}
      <section className="mx-auto max-w-7xl px-6 py-14">

        {/* Section Heading */}
        <div className="mb-8">
          <p className="text-sm font-bold uppercase tracking-widest text-orange-500">
            Discover Local Services
          </p>

          <h2 className="mt-2 text-3xl font-extrabold text-emerald-950">
            Services Available
          </h2>

          <p className="mt-3 text-gray-500">
            Find the right services for your next journey.
          </p>
        </div>

        {/* Search */}
        <div className="mb-6">
          <input
            type="text"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            placeholder="Search by service name, description or district..."
            className="w-full rounded-xl border border-gray-200 bg-white px-5 py-4 text-sm outline-none transition focus:border-emerald-600 focus:ring-2 focus:ring-emerald-100"
          />
        </div>

        {/* Category Filters */}
        <div className="mb-10 flex flex-wrap gap-3">
          {categories.map((category) => (
            <button
              key={category}
              onClick={() => setSelectedCategory(category)}
              className={`rounded-full px-5 py-2.5 text-sm font-semibold transition ${
                selectedCategory === category
                  ? "bg-emerald-700 text-white shadow-md"
                  : "border border-gray-200 bg-white text-gray-600 hover:border-emerald-600 hover:text-emerald-700"
              }`}
            >
              {category.replaceAll("_", " ")}
            </button>
          ))}
        </div>

        {/* Loading State */}
        {loading && (
          <div className="py-16 text-center">
            <div className="mx-auto h-12 w-12 animate-spin rounded-full border-4 border-emerald-200 border-t-emerald-700" />

            <p className="mt-4 font-semibold text-emerald-800">
              Loading tourism services...
            </p>
          </div>
        )}

        {/* Error State */}
        {!loading && error && (
          <div className="rounded-2xl bg-white p-10 text-center shadow-sm">
            <p className="font-semibold text-red-600">{error}</p>

            <button
              onClick={fetchServices}
              className="mt-5 rounded-lg bg-emerald-700 px-6 py-3 font-semibold text-white transition hover:bg-emerald-800"
            >
              Try Again
            </button>
          </div>
        )}

        {/* Services Grid */}
        {!loading && !error && filteredServices.length > 0 && (
          <>
            <p className="mb-5 text-sm text-gray-500">
              Showing {filteredServices.length} of {services.length} services
            </p>

            <div className="grid grid-cols-1 gap-7 sm:grid-cols-2 lg:grid-cols-3">
              {filteredServices.map((service) => (
                <ServiceCard
                  key={service.id}
                  service={service}
                />
              ))}
            </div>
          </>
        )}

        {/* Empty State */}
        {!loading && !error && filteredServices.length === 0 && (
          <div className="rounded-2xl bg-white px-6 py-16 text-center shadow-sm">
            <div className="text-5xl">🔍</div>

            <h3 className="mt-4 text-xl font-bold text-emerald-950">
              No Services Found
            </h3>

            <p className="mt-2 text-gray-500">
              Try another search term or category.
            </p>

            <button
              onClick={() => {
                setSearch("");
                setSelectedCategory("ALL");
              }}
              className="mt-5 font-semibold text-emerald-700 hover:text-orange-500"
            >
              Clear Filters
            </button>
          </div>
        )}

      </section>
    </main>
  );
}

export default Services;
