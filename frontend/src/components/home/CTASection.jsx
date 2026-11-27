import { Link } from "react-router-dom";

function CTASection() {
  return (
    <section className="bg-gray-50 px-6 py-8 md:py-10">
      <div className="relative mx-auto max-w-7xl overflow-hidden rounded-2xl bg-gradient-to-br from-emerald-950 via-emerald-800 to-teal-900 px-6 py-8 text-center shadow-lg md:px-12 md:py-10">

        {/* Decorative Background */}
        <div className="pointer-events-none absolute -left-20 -top-20 h-48 w-48 rounded-full bg-emerald-400/20 blur-3xl" />
        <div className="pointer-events-none absolute -bottom-20 -right-16 h-52 w-52 rounded-full bg-orange-400/20 blur-3xl" />

        <div className="relative z-10 mx-auto max-w-4xl">

          <span className="inline-block rounded-full border border-orange-300/30 bg-orange-400/10 px-4 py-1.5 text-xs font-bold uppercase tracking-widest text-orange-300">
            Your Next Adventure Awaits
          </span>

          <h2 className="mt-4 text-2xl font-extrabold leading-tight text-white md:text-4xl">
            Discover More.{" "}
            <span className="text-orange-400">Experience More.</span>
            <span className="block">Explore Eastern India.</span>
          </h2>

          <p className="mx-auto mt-3 max-w-2xl text-sm leading-6 text-emerald-100">
            From ancient heritage and spiritual journeys to breathtaking
            landscapes and vibrant local culture, your next unforgettable
            experience starts here.
          </p>

          <div className="mt-6 flex flex-wrap justify-center gap-3">
            <Link
              to="/destinations"
              className="inline-flex items-center justify-center rounded-lg bg-orange-500 px-6 py-3 text-sm font-bold text-white shadow-md transition duration-300 hover:-translate-y-1 hover:bg-orange-400"
            >
              Explore Destinations →
            </Link>

            <Link
              to="/guides"
              className="inline-flex items-center justify-center rounded-lg border border-white/40 bg-white/10 px-6 py-3 text-sm font-bold text-white backdrop-blur-sm transition duration-300 hover:-translate-y-1 hover:bg-white/20"
            >
              Find Local Guides →
            </Link>
          </div>

          <div className="mx-auto mt-5 h-px max-w-xs bg-gradient-to-r from-transparent via-white/30 to-transparent" />

          <p className="mt-3 text-xs font-medium tracking-wide text-emerald-200/80">
            HERITAGE · CULTURE · NATURE · EXPERIENCES
          </p>

        </div>
      </div>
    </section>
  );
}

export default CTASection;