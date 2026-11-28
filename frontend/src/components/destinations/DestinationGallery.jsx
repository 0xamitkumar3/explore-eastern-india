import { useEffect, useState } from "react";
import api from "../../services/api";

function DestinationGallery({ destinationId }) {
  const [images, setImages] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);

  useEffect(() => {
    const fetchImages = async () => {
      try {
        setLoading(true);
        setError(false);

        const response = await api.get(
          `/destinations/${destinationId}/images`
        );

        setImages(response.data);
      } catch (err) {
        console.error("Error loading gallery:", err);
        setError(true);
      } finally {
        setLoading(false);
      }
    };

    if (destinationId) {
      fetchImages();
    }
  }, [destinationId]);

  if (loading) {
    return (
      <div className="py-8 text-center text-gray-500">
        Loading gallery...
      </div>
    );
  }

  if (error) {
    return (
      <p className="py-8 text-center text-gray-500">
        Unable to load destination gallery.
      </p>
    );
  }

  if (images.length === 0) {
    return (
      <p className="py-8 text-center text-gray-500">
        No images available for this destination yet.
      </p>
    );
  }

  return (
    <section className="mt-12">
      <div className="mb-6">
        <p className="text-sm font-bold uppercase tracking-widest text-orange-500">
          Visual Journey
        </p>

        <h2 className="mt-2 text-2xl font-extrabold text-emerald-950 md:text-3xl">
          Destination Gallery
        </h2>

        <p className="mt-2 text-gray-500">
          Explore beautiful moments from this destination.
        </p>
      </div>

      <div className="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-3">
        {images.map((image) => (
          <div
            key={image.id}
            className="group overflow-hidden rounded-2xl bg-white shadow-sm"
          >
            <img
              src={`http://localhost:8080/api/destinations/images/${image.id}/file`}
              alt={image.imageName}
              loading="lazy"
              className="h-64 w-full object-cover transition duration-500 group-hover:scale-105"
            />

            <div className="p-4">
              <p className="font-semibold text-emerald-900">
                {image.imageName}
              </p>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
}

export default DestinationGallery;
