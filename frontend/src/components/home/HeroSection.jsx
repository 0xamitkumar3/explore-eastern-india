import { useNavigate } from "react-router-dom";
import Button from "../common/Button";

function HeroSection() {
  const navigate = useNavigate();

  const heroImage =
    "https://s7ap1.scene7.com/is/image/incredibleindia/gaya-mahabodhi%20temple-gaya-bihar?qlt=82&ts=1726740906287";

  return (
    <section
      className="relative flex min-h-[480px] items-center bg-cover bg-center px-6 py-20 text-white md:min-h-[520px]"
      style={{ backgroundImage: `url(${heroImage})` }}
    >
      {/* Gradient Overlay */}
      <div className="absolute inset-0 bg-gradient-to-r from-black/85 via-black/55 to-black/10" />

      <div className="relative z-10 mx-auto w-full max-w-7xl">
        <div className="max-w-2xl">
          <p className="mb-5 text-sm font-bold uppercase tracking-[0.25em] text-orange-400">
            Discover Eastern India
          </p>

          <h1 className="text-4xl font-extrabold leading-tight md:text-6xl">
            Explore the Beauty of
            <span className="block text-orange-400">
              Eastern India
            </span>
          </h1>

          <p className="mt-6 max-w-xl text-base leading-7 text-gray-200 md:text-lg">
            Discover ancient heritage, spiritual destinations,
            vibrant culture and unforgettable experiences.
          </p>

          <div className="mt-8 flex flex-wrap gap-4">
            <Button
              size="lg"
              onClick={() => navigate("/destinations")}
            >
              Explore Destinations
            </Button>

            <Button
              size="lg"
              variant="outline"
              className="border-white text-white hover:bg-white hover:text-emerald-900"
              onClick={() => navigate("/services")}
            >
              Discover More
            </Button>
          </div>
        </div>
      </div>
    </section>
  );
}

export default HeroSection;
