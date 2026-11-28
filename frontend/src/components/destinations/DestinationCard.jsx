import { Link } from "react-router-dom";
import Button from "../common/Button";

function DestinationCard({ destination }) {
  const {
    id,
    name,
    category,
    description,
    district,
    state,
    imageUrl,
  } = destination;

  const image = imageUrl
    ? `http://localhost:8080${imageUrl}`
    : null;

  return (
    <div className="group overflow-hidden rounded-2xl border border-gray-100 bg-white shadow-sm transition duration-300 hover:-translate-y-1 hover:shadow-xl">

      {/* Destination Image */}
      <div className="relative h-56 overflow-hidden bg-gray-100">
        {image ? (
          <img
  src={image}
  alt={name}
  loading="lazy"
  onLoad={() => console.log("Image loaded:", image)}
  onError={() => console.error("Image failed:", image)}
  className="h-full w-full object-cover"
/>
        ) : (
          <div className="flex h-full items-center justify-center bg-gradient-to-br from-emerald-100 to-orange-100 text-6xl">
            🏛️
          </div>
        )}

        {category && (
          <span className="absolute left-4 top-4 rounded-full bg-orange-500 px-3 py-1 text-xs font-semibold text-white shadow">
            {category}
          </span>
        )}
      </div>

      {/* Destination Details */}
      <div className="p-5">
        <h3 className="text-xl font-bold text-emerald-900 transition group-hover:text-orange-500">
          {name}
        </h3>

        {(district || state) && (
          <p className="mt-2 flex items-center gap-2 text-sm text-gray-500">
            <span>📍</span>
            {[district, state].filter(Boolean).join(", ")}
          </p>
        )}

        <p className="mt-3 line-clamp-3 text-sm leading-6 text-gray-600">
          {description ||
            "Discover the history, culture and beauty of this destination."}
        </p>

        <div className="mt-5">
          <Link to={`/destinations/${id}`}>
            <Button size="md" block>
              View Details →
            </Button>
          </Link>
        </div>
      </div>
    </div>
  );
}

export default DestinationCard;