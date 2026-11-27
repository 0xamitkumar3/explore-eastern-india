
import Button from "../components/common/Button";
import Card from "../components/common/Card";
import SectionHeading from "../components/common/SectionHeading";

function Home() {
  return (
    <main className="min-h-screen bg-gray-50 px-6 py-16">
      <div className="mx-auto max-w-7xl">

        {/* Existing Home Heading */}
        <div className="mb-16 text-center">
          <h1 className="text-4xl font-bold text-emerald-800">
            Explore Eastern India
          </h1>

          <p className="mt-4 text-lg text-gray-600">
            Discover the culture, heritage and beautiful destinations
            of Eastern India.
          </p>
        </div>

        {/* Reusable Section Heading */}
        <SectionHeading
          subtitle="Discover Bihar"
          title="Explore Amazing Destinations"
          description="Experience the rich history, culture and natural beauty of Bihar."
        />

        {/* Destination Cards */}
        <div className="grid grid-cols-1 gap-8 md:grid-cols-2 lg:grid-cols-3">

          <Card
            title="Bodh Gaya"
            category="Historical Destination"
            description="Explore the sacred place where Gautama Buddha attained enlightenment."
          >
            <Button onClick={() => alert("Exploring Bodh Gaya")}>
              Explore More
            </Button>
          </Card>

          <Card
            title="Nalanda"
            category="Cultural Heritage"
            description="Discover the ancient centre of learning and Buddhist heritage."
          >
            <Button variant="secondary">
              Learn More
            </Button>
          </Card>

          <Card
            title="Rajgir"
            category="Nature & Heritage"
            description="Experience beautiful hills, historical sites and peaceful landscapes."
          >
            <Button variant="outline">
              View Details
            </Button>
          </Card>

        </div>
      </div>
    </main>
  );
}

export default Home;
