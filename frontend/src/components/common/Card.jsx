function Card({
  image,
  title,
  description,
  category,
  children,
  className = "",
}) {
  return (
    <div
      className={`overflow-hidden rounded-xl border border-gray-200 bg-white shadow-sm transition duration-300 hover:-translate-y-1 hover:shadow-lg ${className}`}
    >
      {/* Image */}
      {image && (
        <div className="h-52 w-full overflow-hidden">
          <img
            src={image}
            alt={title}
            className="h-full w-full object-cover transition duration-500 hover:scale-105"
          />
        </div>
      )}

      {/* Content */}
      <div className="p-5">
        {category && (
          <span className="mb-3 inline-block rounded-full bg-emerald-50 px-3 py-1 text-xs font-semibold text-emerald-700">
            {category}
          </span>
        )}

        <h3 className="text-xl font-bold text-gray-900">
          {title}
        </h3>

        <p className="mt-2 text-sm leading-6 text-gray-600">
          {description}
        </p>

        {children && (
          <div className="mt-5">
            {children}
          </div>
        )}
      </div>
    </div>
  );
}

export default Card;
