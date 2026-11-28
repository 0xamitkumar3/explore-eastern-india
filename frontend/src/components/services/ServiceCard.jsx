
function ServiceCard({ service }) {
  const {
    name,
    category,
    description,
    address,
    district,
    state,
    contactNumber,
    verified,
  } = service;

  const categoryIcons = {
    HOTEL: "🏨",
    RESTAURANT: "🍽️",
    TRANSPORT: "🚗",
    TOUR_OPERATOR: "🧭",
  };

  const icon = categoryIcons[category] || "🌍";

  const formattedCategory = category
    ?.replaceAll("_", " ")
    .toLowerCase()
    .replace(/\b\w/g, (char) => char.toUpperCase());

  return (
    <article className="group overflow-hidden rounded-2xl border border-gray-100 bg-white shadow-sm transition duration-300 hover:-translate-y-1 hover:shadow-xl">

      {/* Card Header */}
      <div className="flex items-center justify-between bg-gradient-to-r from-emerald-50 to-orange-50 p-5">
        <div className="flex h-14 w-14 items-center justify-center rounded-xl bg-white text-3xl shadow-sm">
          {icon}
        </div>

        <span className="rounded-full bg-white px-3 py-1 text-xs font-bold text-emerald-800 shadow-sm">
          {formattedCategory || "Tourism Service"}
        </span>
      </div>

      {/* Card Content */}
      <div className="p-6">

        <h3 className="text-xl font-bold text-emerald-950 transition group-hover:text-orange-500">
          {name}
        </h3>

        <p className="mt-3 min-h-12 text-sm leading-6 text-gray-600">
          {description || "Discover quality tourism services."}
        </p>

        {/* Location */}
        <div className="mt-5 space-y-3 border-t border-gray-100 pt-4">

          <p className="flex items-start gap-2 text-sm text-gray-600">
            <span>📍</span>
            <span>
              {address && <>{address}, </>}
              {[district, state].filter(Boolean).join(", ")}
            </span>
          </p>

          {contactNumber && (
            <p className="flex items-center gap-2 text-sm text-gray-600">
              <span>📞</span>
              {contactNumber}
            </p>
          )}

        </div>

        {/* Verification Status */}
        <div className="mt-5 border-t border-gray-100 pt-4">
          {verified ? (
            <span className="inline-flex items-center gap-2 rounded-full bg-emerald-50 px-3 py-2 text-xs font-semibold text-emerald-700">
              ✓ Verified Service
            </span>
          ) : (
            <span className="inline-flex items-center gap-2 rounded-full bg-orange-50 px-3 py-2 text-xs font-semibold text-orange-700">
              ⚠ Not Verified
            </span>
          )}
        </div>

      </div>
    </article>
  );
}

export default ServiceCard;
