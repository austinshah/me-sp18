package synthesizer;

//Make sure this class is public
public class GuitarString {
    private static final int SR = 44100;
    private static final double DECAY = .996;

    private BoundedQueue<Double> buffer;

    public GuitarString(double frequency) {
        double capacity = Math.round(SR / frequency);
        buffer = new ArrayRingBuffer((int) capacity);
        while (!buffer.isFull()) {
            buffer.enqueue(0.0);
        }
    }

    public void pluck() {
        while (!buffer.isEmpty()) {
            buffer.dequeue();
        }

        while (!buffer.isFull()) {
            double r = Math.random() - 0.5;
            buffer.enqueue(r);
        }
    }

    public void tic() {
        double first = buffer.dequeue();
        double second = buffer.peek();
        double newSamp = ((first + second) / 2) * DECAY;
        buffer.enqueue(newSamp);
    }

    public double sample() {
        return buffer.peek();
    }
}
