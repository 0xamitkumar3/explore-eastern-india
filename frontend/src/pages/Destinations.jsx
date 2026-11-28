
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import Button from "../components/common/Button";
import SectionHeading from "../components/common/SectionHeading";
import DestinationCard from "../components/destinations/DestinationCard";

import { getAllDestinations } from "../services/destinationService";

function Destinations() {
  const [destinations, setDestinations] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [searchTerm, setSearchTerm] = useState("");
  const [selectedCategory, setSelectedCategory] = useState("All");
  const [selectedDistrict, setSelectedDistrict] = useState("All");

  // Fetch destinations from backend
  const fetchDestinations = async () => {
    setLoading(true);
    setError("");

    try {
      const data = await getAllDestinations();
      setDestinations(data);
    } catch (err) {
      console.error("Error fetching destinations:", err);
      setError("Unable to load destinations. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchDestinations();
  }, []);

  // Get unique categories dynamically from API
  const categories = [
    ...new Set(
      destinations
        .map((destination) => destination.category)
        .filter(Boolean)
    ),
  ].sort();

  // Get unique districts dynamically from API
  const districts = [
    ...new Set(
      destinations
        .map((destination) => destination.district)
        .filter(Boolean)
    ),
  ].sort();

  // Apply search, category and district filters
  const filteredDestinations = destinations.filter((destination) => {
    const matchesSearch = destination.name
      .toLowerCase()
      .includes(searchTerm.trim().toLowerCase());

    const matchesCategory =
      selectedCategory === "All" ||
      destination.category === selectedCategory;

    const matchesDistrict =
      selectedDistrict === "All" ||
      destination.district === selectedDistrict;

    return matchesSearch && matchesCategory && matchesDistrict;
  });

  // Clear all filters
  const handleClearFilters = () => {
    setSearchTerm("");
    setSelectedCategory("All");
    setSelectedDistrict("All");
  };

  return (
    <main className="min-h-screen bg-gray-50">

      {/* Page Hero */}
      <section className="bg-gradient-to-br from-emerald-950 via-emerald-800 to-teal-900 px-6 py-14 text-center md:py-20">
        <div className="mx-auto max-w-3xl">
          <p className="mb-3 text-sm font-bold uppercase tracking-widest text-orange-400">
            Discover Eastern India
          </p>

          <h1 className="text-3xl font-extrabold text-white md:text-5xl">
            Explore Amazing Destinations
          </h1>

          <p className="mx-auto mt-5 max-w-2xl text-sm leading-7 text-emerald-100 md:text-base">
            Discover the rich heritage, spiritual destinations,
            breathtaking landscapes and hidden gems of Bihar.
          </p>

          <div className="mt-7">
            <Link to="/">
              <Button
                variant="outline"
                className="border-white text-white hover:bg-white/10"
              >
                Back to Home
              </Button>
            </Link>
          </div>
        </div>
      </section>

      {/* Destinations Content */}
      <section className="mx-auto max-w-7xl px-6 py-12 md:py-14">

        <SectionHeading
          subtitle="Find Your Next Adventure"
          title="Discover Our Destinations"
          description="Explore remarkable places and plan your next unforgettable journey."
        />

        {/* Search and Filters */}
        <div className="mb-8 grid grid-cols-1 gap-4 md:grid-cols-3">

          {/* Search */}
          <input
            type="text"
            placeholder="Search destinations..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="w-full rounded-xl border border-gray-200 bg-white px-5 py-3 text-sm outline-none transition focus:border-emerald-600 focus:ring-2 focus:ring-emerald-100"
          />

          {/* Category Filter */}
          <select
            value={selectedCategory}
            onChange={(e) => setSelectedCategory(e.target.value)}
            className="w-full cursor-pointer rounded-xl border border-gray-200 bg-white px-5 py-3 text-sm outline-none transition focus:border-emerald-600 focus:ring-2 focus:ring-emerald-100"
          >
            <option value="All">All Categories</option>

            {categories.map((category) => (
              <option key={category} value={category}>
                {category}
              </option>
            ))}
          </select>

          {/* District Filter */}
          <select
            value={selectedDistrict}
            onChange={(e) => setSelectedDistrict(e.target.value)}
            className="w-full cursor-pointer rounded-xl border border-gray-200 bg-white px-5 py-3 text-sm outline-none transition focus:border-emerald-600 focus:ring-2 focus:ring-emerald-100"
          >
            <option value="All">All Districts</option>

            {districts.map((district) => (
              <option key={district} value={district}>
                {district}
              </option>
            ))}
          </select>

        </div>

        {/* Loading State */}
        {loading && (
          <div className="py-16 text-center">
            <div className="mx-auto h-10 w-10 animate-spin rounded-full border-4 border-emerald-200 border-t-emerald-700" />

            <p className="mt-4 font-semibold text-emerald-800">
              Loading destinations...
            </p>
          </div>
        )}

        {/* Error State */}
        {!loading && error && (
          <div className="rounded-xl bg-red-50 px-6 py-10 text-center">
            <p className="font-semibold text-red-600">
              {error}
            </p>

            <button
              onClick={fetchDestinations}
              className="mt-4 rounded-lg bg-red-500 px-5 py-2 text-sm font-semibold text-white transition hover:bg-red-600"
            >
              Try Again
            </button>
          </div>
        )}

        {/* No Destinations */}
        {!loading && !error && destinations.length === 0 && (
          <div className="py-16 text-center">
            <p className="text-lg font-semibold text-gray-700">
              No destinations available.
            </p>
          </div>
        )}

        {/* Destination Cards */}
        {!loading && !error && destinations.length > 0 && (
          <>
            <div className="mb-5 flex flex-wrap items-center justify-between gap-3">
              <p className="text-sm text-gray-500">
                Showing {filteredDestinations.length} of{" "}
                {destinations.length} destinations
              </p>

              {(searchTerm ||
                selectedCategory !== "All" ||
                selectedDistrict !== "All") && (
                <button
                  onClick={handleClearFilters}
                  className="text-sm font-semibold text-emerald-700 transition hover:text-orange-500"
                >
                  Clear Filters ✕
                </button>
              )}
            </div>

            {filteredDestinations.length > 0 ? (
              <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
                {filteredDestinations.map((destination) => (
                  <DestinationCard
                    key={destination.id}
                    destination={destination}
                  />
                ))}
              </div>
            ) : (
              <div className="py-16 text-center">
                <p className="text-lg font-semibold text-gray-700">
                  No destinations found.
                </p>

                <p className="mt-2 text-sm text-gray-500">
                  Try changing your search or filters.
                </p>

                <button
                  onClick={handleClearFilters}
                  className="mt-4 rounded-lg bg-emerald-700 px-5 py-2 text-sm font-semibold text-white transition hover:bg-emerald-800"
                >
                  Clear Filters
                </button>
              </div>
            )}
          </>
        )}

      </section>
    </main>
  );
}

export default Destinations;
