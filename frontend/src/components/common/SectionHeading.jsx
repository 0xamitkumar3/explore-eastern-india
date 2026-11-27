function SectionHeading({
  subtitle,
  title,
  description,
  align = "center",
  className = "",
}) {
  const alignment = {
    center: "text-center",
    left: "text-left",
  };

  return (
    <div className={`mb-10 ${alignment[align]} ${className}`}>
      {subtitle && (
        <p className="mb-3 text-sm font-semibold uppercase tracking-widest text-orange-500">
          {subtitle}
        </p>
      )}

      <h2 className="text-3xl font-bold text-emerald-800 md:text-4xl">
        {title}
      </h2>

      {description && (
        <p className="mx-auto mt-4 max-w-2xl text-base leading-7 text-gray-600">
          {description}
        </p>
      )}
    </div>
  );
}

export default SectionHeading;
