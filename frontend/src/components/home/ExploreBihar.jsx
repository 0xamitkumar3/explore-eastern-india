import { Link } from "react-router-dom";

import Button from "../common/Button";

import bodhGayaImage from "../../assets/images/bodh-gaya/bodh.jpeg";
import nalandaImage from "../../assets/images/nalanda/nalanda-2.jpeg";
import rajgirImage from "../../assets/images/rajgir/glass-bridge.jpeg";
import vaishaliImage from "../../assets/images/other/monastery.jpeg";

const highlights = [
  {
    name: "Bodh Gaya",
    subtitle: "The Land of Enlightenment",
    image: bodhGayaImage,
  },
  {
    name: "Nalanda",
    subtitle: "A Glimpse into Ancient Wisdom",
    image: nalandaImage,
  },
  {
    name: "Rajgir",
    subtitle: "Nature, Hills and Adventure",
    image: rajgirImage,
  },
  {
    name: "Vaishali",
    subtitle: "The Cradle of Ancient History",
    image: vaishaliImage,
  },
];

function ExploreBihar() {
  return (
    <section className="bg-gray-50 px-6 py-8 md:py-10">
      <div className="mx-auto max-w-7xl">
        <div className="mb-6 text-center">
          <p className="mb-2 text-sm font-bold uppercase tracking-widest text-orange-500">
            Experience the Culture
          </p>

          <h2 className="text-3xl font-bold text-emerald-800 md:text-4xl">
            Explore the Heart of Bihar
          </h2>

          <p className="mx-auto mt-3 max-w-2xl text-gray-600">
            Discover remarkable places filled with history, spirituality,
            natural beauty and unforgettable experiences.
          </p>
        </div>

        <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-4">
          {highlights.map((place) => (
            <div
              key={place.name}
              className="group relative h-80 overflow-hidden rounded-2xl shadow-md"
            >
              <img
                src={place.image}
                alt={place.name}
                className="h-full w-full object-cover transition duration-500 group-hover:scale-110"
              />

              <div className="absolute inset-0 bg-gradient-to-t from-black/80 via-black/20 to-transparent" />

              <div className="absolute bottom-0 left-0 p-6 text-white">
                <h3 className="text-2xl font-bold">{place.name}</h3>

                <p className="mt-2 text-sm text-gray-200">
                  {place.subtitle}
                </p>

                <Link
                  to="/destinations"
                  className="mt-4 inline-block text-sm font-semibold text-orange-400 transition hover:text-orange-300"
                >
                  Explore More →
                </Link>
              </div>
            </div>
          ))}
        </div>

        <div className="mt-8 text-center">
          <Link to="/destinations">
            <Button size="lg">Discover All Destinations</Button>
          </Link>
        </div>
      </div>
    </section>
  );
}

export default ExploreBihar;
