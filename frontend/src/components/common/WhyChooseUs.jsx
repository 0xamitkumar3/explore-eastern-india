const features = [
  {
    icon: "🏛️",
    title: "Rich Cultural Heritage",
    description:
      "Explore ancient monuments, historical sites and the timeless traditions of Eastern India.",
  },
  {
    icon: "🧭",
    title: "Authentic Experiences",
    description:
      "Discover local culture, traditional food, festivals and unique experiences.",
  },
  {
    icon: "📍",
    title: "Curated Destinations",
    description:
      "Find popular attractions and hidden gems with useful travel information.",
  },
  {
    icon: "🤝",
    title: "Local Travel Support",
    description:
      "Connect with local guides and tourism services to make your journey easier.",
  },
];

function WhyChooseUs() {
  return (
    <section className="bg-white px-6 py-8 md:py-10">
      <div className="mx-auto max-w-7xl">
        <div className="mb-6 text-center">
          <p className="mb-2 text-sm font-bold uppercase tracking-widest text-orange-500">
            Why Explore With Us
          </p>

          <h2 className="text-3xl font-bold text-emerald-800 md:text-4xl">
            Your Journey, Our Priority
          </h2>

          <p className="mx-auto mt-3 max-w-2xl text-gray-600">
            We help you discover the beauty, heritage and culture
            of Eastern India with a better travel experience.
          </p>
        </div>

        <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-4">
          {features.map((feature) => (
            <div
              key={feature.title}
              className="rounded-xl border border-gray-100 bg-gray-50 p-7 text-center transition duration-300 hover:-translate-y-2 hover:shadow-lg"
            >
              <div className="mx-auto mb-4 flex h-16 w-16 items-center justify-center rounded-full bg-emerald-100 text-3xl">
                {feature.icon}
              </div>

              <h3 className="mb-2 text-lg font-bold text-gray-900">
                {feature.title}
              </h3>

              <p className="text-sm leading-7 text-gray-600">
                {feature.description}
              </p>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}

export default WhyChooseUs;
