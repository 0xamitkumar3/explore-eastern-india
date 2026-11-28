
import { useEffect, useMemo, useState } from "react";

import { getAllGuides } from "../services/guideService";
import GuideCard from "../components/guides/GuideCard";

function Guides() {
  const [guides, setGuides] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [search, setSearch] = useState("");
  const [selectedDistrict, setSelectedDistrict] = useState("ALL");

  const fetchGuides = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await getAllGuides();
      setGuides(data);
    } catch (err) {
      console.error("Error fetching guides:", err);
      setError("Unable to load tour guides. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchGuides();
  }, []);

  const districts = useMemo(() => {
    return [
      "ALL",
      ...new Set(guides.map((guide) => guide.district).filter(Boolean)),
    ];
  }, [guides]);

  const filteredGuides = useMemo(() => {
    return guides.filter((guide) => {
      const searchText = search.toLowerCase();

      const matchesSearch =
        guide.name?.toLowerCase().includes(searchText) ||
        guide.description?.toLowerCase().includes(searchText) ||
        guide.languages?.toLowerCase().includes(searchText) ||
        guide.specialization?.toLowerCase().includes(searchText);

      const matchesDistrict =
        selectedDistrict === "ALL" ||
        guide.district === selectedDistrict;

      return matchesSearch && matchesDistrict;
    });
  }, [guides, search, selectedDistrict]);

  return (
    <main className="min-h-screen bg-gray-50">
      {/* Hero Section */}
      <section className="bg-gradient-to-br from-emerald-950 via-emerald-800 to-teal-700 px-6 py-20 text-center">
        <p className="text-sm font-bold uppercase tracking-[0.25em] text-orange-300">
          Explore Eastern India
        </p>

        <h1 className="mt-4 text-4xl font-extrabold text-white md:text-5xl">
          Local Tour Guides
        </h1>

        <p className="mx-auto mt-5 max-w-2xl text-base leading-7 text-emerald-50 md:text-lg">
          Connect with local guides who know the history, culture and
          hidden stories of Eastern India.
        </p>

        <div className="mx-auto mt-8 h-1 w-20 rounded-full bg-orange-400" />
      </section>

      {/* Guides Section */}
      <section className="mx-auto max-w-7xl px-6 py-14">
        <div className="mb-8">
          <p className="text-sm font-bold uppercase tracking-widest text-orange-500">
            Meet Local Experts
          </p>

          <h2 className="mt-2 text-3xl font-extrabold text-emerald-950">
            Find Your Tour Guide
          </h2>

          <p className="mt-3 text-gray-500">
            Discover experienced local guides for your next adventure.
          </p>
        </div>

        {/* Search */}
        <div className="mb-6">
          <input
            type="text"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            placeholder="Search by name, language or specialization..."
            className="w-full rounded-xl border border-gray-200 bg-white px-5 py-4 text-sm outline-none transition focus:border-emerald-600 focus:ring-2 focus:ring-emerald-100"
          />
        </div>

        {/* District Filters */}
        <div className="mb-10 flex flex-wrap gap-3">
          {districts.map((district) => (
            <button
              key={district}
              onClick={() => setSelectedDistrict(district)}
              className={`rounded-full px-5 py-2.5 text-sm font-semibold transition ${
                selectedDistrict === district
                  ? "bg-emerald-700 text-white shadow-md"
                  : "border border-gray-200 bg-white text-gray-600 hover:border-emerald-600 hover:text-emerald-700"
              }`}
            >
              {district}
            </button>
          ))}
        </div>

        {/* Loading */}
        {loading && (
          <div className="py-16 text-center">
            <div className="mx-auto h-12 w-12 animate-spin rounded-full border-4 border-emerald-200 border-t-emerald-700" />

            <p className="mt-4 font-semibold text-emerald-800">
              Loading tour guides...
            </p>
          </div>
        )}

        {/* Error */}
        {!loading && error && (
          <div className="rounded-2xl bg-white p-10 text-center shadow-sm">
            <p className="font-semibold text-red-600">{error}</p>

            <button
              onClick={fetchGuides}
              className="mt-5 rounded-lg bg-emerald-700 px-6 py-3 font-semibold text-white transition hover:bg-emerald-800"
            >
              Try Again
            </button>
          </div>
        )}

        {/* Guide Cards */}
        {!loading && !error && filteredGuides.length > 0 && (
          <>
            <p className="mb-5 text-sm text-gray-500">
              Showing {filteredGuides.length} of {guides.length} guides
            </p>

            <div className="grid grid-cols-1 gap-7 sm:grid-cols-2 lg:grid-cols-3">
              {filteredGuides.map((guide) => (
                <GuideCard key={guide.id} guide={guide} />
              ))}
            </div>
          </>
        )}

        {/* Empty State */}
        {!loading && !error && filteredGuides.length === 0 && (
          <div className="rounded-2xl bg-white px-6 py-16 text-center shadow-sm">
            <div className="text-5xl">🔍</div>

            <h3 className="mt-4 text-xl font-bold text-emerald-950">
              No Guides Found
            </h3>

            <p className="mt-2 text-gray-500">
              Try another search term or district.
            </p>

            <button
              onClick={() => {
                setSearch("");
                setSelectedDistrict("ALL");
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

export default Guides;
