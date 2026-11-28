
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

import { getDestinationById } from "../services/destinationService";
import DestinationGallery from "../components/destinations/DestinationGallery";

function DestinationDetails() {
  const { id } = useParams();

  const [destination, setDestination] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [imageError, setImageError] = useState(false);

  // Fetch destination details
  const fetchDestination = async () => {
    setLoading(true);
    setError("");
    setImageError(false);

    try {
      const data = await getDestinationById(id);
      setDestination(data);
    } catch (err) {
      console.error("Error fetching destination:", err);
      setError(
        "Unable to load destination details. Please try again."
      );
      setDestination(null);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchDestination();
  }, [id]);

  // Loading State
  if (loading) {
    return (
      <main className="flex min-h-screen items-center justify-center bg-gray-50">
        <div className="text-center">
          <div className="mx-auto h-12 w-12 animate-spin rounded-full border-4 border-emerald-200 border-t-emerald-700" />

          <p className="mt-4 font-semibold text-emerald-800">
            Loading destination details...
          </p>
        </div>
      </main>
    );
  }

  // Error State
  if (error) {
    return (
      <main className="flex min-h-screen items-center justify-center bg-gray-50 px-6">
        <div className="max-w-md text-center">
          <p className="text-xl font-bold text-red-600">
            Something went wrong!
          </p>

          <p className="mt-3 text-gray-600">
            {error}
          </p>

          <button
            onClick={fetchDestination}
            className="mt-6 rounded-lg bg-emerald-700 px-6 py-3 font-semibold text-white transition hover:bg-emerald-800"
          >
            Try Again
          </button>

          <div className="mt-4">
            <Link
              to="/destinations"
              className="font-semibold text-emerald-700 hover:text-orange-500"
            >
              Back to Destinations
            </Link>
          </div>
        </div>
      </main>
    );
  }

  // Destination Not Found
  if (!destination) {
    return (
      <main className="flex min-h-screen items-center justify-center bg-gray-50">
        <div className="text-center">
          <h2 className="text-2xl font-bold text-gray-800">
            Destination Not Found
          </h2>

          <p className="mt-3 text-gray-500">
            The destination you are looking for does not exist.
          </p>

          <Link
            to="/destinations"
            className="mt-6 inline-block rounded-lg bg-emerald-700 px-6 py-3 font-semibold text-white hover:bg-emerald-800"
          >
            Explore Destinations
          </Link>
        </div>
      </main>
    );
  }

  // Database image URL
  const image = destination.imageUrl
    ? `http://localhost:8080${destination.imageUrl}`
    : null;

  // Entry Fee
  const entryFee =
    destination.entryFeeVerified
      ? `₹${Number(destination.entryFee).toFixed(2)}`
      : destination.entryFee != null
        ? `₹${Number(destination.entryFee).toFixed(2)} (Unverified)`
        : "Not available";

  // Best Time to Visit
  const bestTimeToVisit = destination.bestTimeVerified
    ? destination.bestTimeToVisit || "Not available"
    : destination.bestTimeToVisit
      ? `${destination.bestTimeToVisit} (Unverified)`
      : "Not available";

  return (
    <main className="min-h-screen bg-gray-50">

      {/* Hero Image */}
      <section className="relative h-[350px] overflow-hidden bg-gradient-to-br from-emerald-950 to-teal-800 md:h-[480px]">

        {image && !imageError && (
          <img
            src={image}
            alt={destination.name}
            onError={() => setImageError(true)}
            className="absolute inset-0 h-full w-full object-cover object-top"
          />
        )}

        <div className="absolute inset-0 bg-gradient-to-t from-black/80 via-black/30 to-black/10" />

        <div className="relative mx-auto flex h-full max-w-7xl flex-col justify-end px-6 pb-12">

          <Link
            to="/destinations"
            className="mb-auto mt-8 w-fit rounded-lg border border-white/40 bg-black/20 px-4 py-2 text-sm font-semibold text-white backdrop-blur transition hover:bg-white/20"
          >
            ← Back to Destinations
          </Link>

          <span className="mb-4 w-fit rounded-full bg-orange-500 px-4 py-2 text-xs font-bold uppercase tracking-wider text-white">
            {destination.category || "Destination"}
          </span>

          <h1 className="text-3xl font-extrabold text-white md:text-5xl">
            {destination.name}
          </h1>

          <p className="mt-3 flex items-center gap-2 text-sm text-white/90 md:text-base">
            <span>📍</span>
            {[destination.district, destination.state]
              .filter(Boolean)
              .join(", ")}
          </p>
        </div>
      </section>

      {/* Destination Information */}
      <section className="mx-auto grid max-w-7xl grid-cols-1 gap-10 px-6 py-12 md:grid-cols-3 md:py-16">

        {/* Main Description */}
        <div className="md:col-span-2">

          <p className="text-sm font-bold uppercase tracking-widest text-orange-500">
            Discover and Explore
          </p>

          <h2 className="mt-3 text-2xl font-extrabold text-emerald-950 md:text-3xl">
            About {destination.name}
          </h2>

          <p className="mt-6 text-base leading-8 text-gray-600">
            {destination.description ||
              "Explore the history, culture and beauty of this remarkable destination."}
          </p>

          {/* Location Information */}
          <div className="mt-10 rounded-2xl border border-gray-100 bg-white p-6 shadow-sm">

            <h3 className="text-xl font-bold text-emerald-900">
              Location Information
            </h3>

            <div className="mt-5 grid grid-cols-1 gap-5 sm:grid-cols-2">

              <div>
                <p className="text-sm text-gray-500">
                  District
                </p>
                <p className="mt-1 font-semibold text-gray-800">
                  {destination.district || "Not available"}
                </p>
              </div>

              <div>
                <p className="text-sm text-gray-500">
                  State
                </p>
                <p className="mt-1 font-semibold text-gray-800">
                  {destination.state || "Not available"}
                </p>
              </div>

              <div>
                <p className="text-sm text-gray-500">
                  Category
                </p>
                <p className="mt-1 font-semibold text-gray-800">
                  {destination.category || "Not available"}
                </p>
              </div>

              <div>
                <p className="text-sm text-gray-500">
                  Destination ID
                </p>
                <p className="mt-1 font-semibold text-gray-800">
                  {destination.id}
                </p>
              </div>

            </div>
          </div>
        </div>

        {/* Quick Information */}
        <aside className="h-fit rounded-2xl border border-gray-100 bg-white p-6 shadow-sm">

          <h3 className="text-xl font-bold text-emerald-900">
            Travel Information
          </h3>

          {/* Entry Fee */}
          <div className="mt-6 border-b border-gray-100 pb-5">
            <p className="text-sm text-gray-500">
              Entry Fee
            </p>

            <p className="mt-2 text-lg font-bold text-gray-800">
              {entryFee}
            </p>

            {!destination.entryFeeVerified && (
              <p className="mt-1 text-xs text-orange-600">
                Fee information is not verified.
              </p>
            )}
          </div>

          {/* Best Time to Visit */}
          <div className="pt-5">
            <p className="text-sm text-gray-500">
              Best Time to Visit
            </p>

            <p className="mt-2 font-bold text-gray-800">
              {bestTimeToVisit}
            </p>

            {!destination.bestTimeVerified && (
              <p className="mt-1 text-xs text-orange-600">
                Information is not verified.
              </p>
            )}
          </div>

          <Link
            to="/destinations"
            className="mt-8 block rounded-xl bg-emerald-700 px-5 py-3 text-center text-sm font-bold text-white transition hover:bg-emerald-800"
          >
            Explore More Destinations
          </Link>

        </aside>
      </section>
      {/* Destination Image Gallery */}
      <div className="mx-auto max-w-7xl px-6 pb-16">
        <DestinationGallery destinationId={destination.id} />
      </div>
    </main>
  );
}

export default DestinationDetails;
