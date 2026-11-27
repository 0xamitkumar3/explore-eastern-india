import { Link } from "react-router-dom";

import Button from "../components/common/Button";
import Card from "../components/common/Card";
import SectionHeading from "../components/common/SectionHeading";
import CTASection from "../components/home/CTASection";

import heroImage from "../assets/images/bodh-gaya/bodh-gaya-hero.png";
import bodhGayaImage from "../assets/images/bodh-gaya/bodh.jpeg";
import nalandaImage from "../assets/images/nalanda/nalanda.jpeg";
import rajgirImage from "../assets/images/rajgir/glass-bridge.jpeg";

import WhyChooseUs from "../components/common/WhyChooseUs";
import ExploreBihar from "../components/home/ExploreBihar";

function Home() {
  return (
    <main className="bg-gray-50">

      
    {/* Hero Section */}
    <section
  className="relative flex min-h-[520px] items-center justify-center bg-cover bg-[center_10%] md:bg-top px-6 py-20 text-center"
  style={{ backgroundImage: `url(${heroImage})` }}
>
      {/* Dark Overlay */}
      <div className="absolute inset-0 bg-black/50"></div>

      {/* Hero Content */}
      <div className="relative z-10 mx-auto max-w-4xl text-white">
        <p className="mb-5 text-sm font-bold uppercase tracking-[0.3em] text-orange-400">
          Discover Eastern India
        </p>

        <h1 className="text-4xl font-extrabold leading-tight md:text-6xl">
          Explore the Beauty of
          <span className="mt-2 block text-orange-400">
            Eastern India
          </span>
        </h1>

        <p className="mx-auto mt-6 max-w-2xl text-base leading-7 text-gray-200 md:text-lg">
          Discover ancient heritage, spiritual destinations,
          vibrant culture and unforgettable experiences.
        </p>

        <div className="mt-9 flex flex-wrap justify-center gap-4">
          <Link to="/destinations">
            <Button size="lg">Explore Destinations</Button>
          </Link>

          <Link to="/services">
            <Button
              variant="outline"
              size="lg"
              className="border-white text-white hover:bg-white/10"
            >
              Discover More
            </Button>
          </Link>
        </div>
      </div>
    </section>


      {/* Destinations Section */}
      <section className="mx-auto max-w-7xl px-6 pt-8 pb-6 md:pt-10 md:pb-8">

        <SectionHeading
          subtitle="Discover Bihar"
          title="Explore Amazing Destinations"
          description="From ancient universities to peaceful spiritual destinations, discover the timeless beauty of Bihar."
        />

        <div className="grid grid-cols-1 gap-8 md:grid-cols-2 lg:grid-cols-3">

          {/* Bodh Gaya */}
          <Card
            image={bodhGayaImage}
            title="Bodh Gaya"
            category="Spiritual Heritage"
            description="Visit the sacred land where Gautama Buddha attained enlightenment."
          >
            <Link to="/destinations">
              <Button>Explore More</Button>
            </Link>
          </Card>

          {/* Nalanda */}
          <Card
            image={nalandaImage}
            title="Nalanda"
            category="Historical Heritage"
            description="Explore the ruins of one of the world's oldest renowned centres of learning."
          >
            <Link to="/destinations">
              <Button>Explore More</Button>
            </Link>
          </Card>

          {/* Rajgir */}
          <Card
            image={rajgirImage}
            title="Rajgir"
            category="Nature & Adventure"
            description="Experience scenic hills, the glass bridge and beautiful landscapes."
          >
            <Link to="/destinations">
              <Button>Explore More</Button>
            </Link>
          </Card>

        </div>
      </section>

      <WhyChooseUs />
      <ExploreBihar />
      <CTASection />
    </main>
  );
}

export default Home;
