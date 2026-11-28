
function GuideCard({ guide }) {
  const {
    name,
    description,
    district,
    state,
    languages,
    specialization,
    contactNumber,
    verified,
  } = guide;

  return (
    <article className="group overflow-hidden rounded-2xl border border-gray-100 bg-white shadow-sm transition duration-300 hover:-translate-y-1 hover:shadow-xl">
      {/* Card Header */}
      <div className="flex items-center gap-4 bg-gradient-to-r from-emerald-50 to-orange-50 p-6">
        <div className="flex h-16 w-16 shrink-0 items-center justify-center rounded-full bg-emerald-700 text-2xl font-bold text-white shadow-sm">
          {name?.charAt(0).toUpperCase() || "G"}
        </div>

        <div>
          <h3 className="text-xl font-bold text-emerald-950 transition group-hover:text-orange-500">
            {name}
          </h3>

          <p className="mt-1 text-sm text-gray-500">
            📍 {district}, {state}
          </p>
        </div>
      </div>

      {/* Card Body */}
      <div className="p-6">
        <span className="inline-block rounded-full bg-orange-50 px-3 py-1.5 text-xs font-bold text-orange-700">
          {specialization || "Local Tour Guide"}
        </span>

        <p className="mt-4 min-h-12 text-sm leading-6 text-gray-600">
          {description || "Explore the destination with a local guide."}
        </p>

        <div className="mt-5 space-y-3 border-t border-gray-100 pt-4">
          <p className="flex items-start gap-2 text-sm text-gray-600">
            <span>🗣️</span>
            <span>
              <strong>Languages:</strong> {languages || "Not specified"}
            </span>
          </p>

          {contactNumber && (
            <p className="flex items-center gap-2 text-sm text-gray-600">
              <span>📞</span>
              {contactNumber}
            </p>
          )}
        </div>

        <div className="mt-5 border-t border-gray-100 pt-4">
          {verified ? (
            <span className="inline-flex items-center gap-2 rounded-full bg-emerald-50 px-3 py-2 text-xs font-semibold text-emerald-700">
              ✓ Verified Guide
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

export default GuideCard;
