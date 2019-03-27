package model.paint;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.lang.String;
import java.awt.Toolkit;
import java.awt.MediaTracker;

import model.Ball;

/**
 * Paint strategy that paints an image from a file, scaled to the host Ball's radius.
 * Note that this class cannot be used by the BallWar system directly as it is lacking a no-parameter constructor.
 * 
 * @author sgj1
 * @author psd2
 *
 */
public class ImagePaintStrategy extends APaintStrategy {

	/**
	 * The percentage of the average of the width and height of the image that defines a unit radius for the image.
	 */
	private double fillFactor = 0.5;

	/**
	 * The image to paint. Set in constructor.
	 */
	private Image image;

	/**
	 * ImageObserver needed for some image operations. Set in init().
	 */
	private ImageObserver imageObs;

	/**
	 * A local affine transform used to transform the image into its unit size and location.
	 */
	protected AffineTransform localAT = new AffineTransform();

	/**
	 * Ratio of the unit radius circle to the effective radius size of the image. Set in init().
	 */
	private double scaleFactor;

	/**
	 * Constructor that takes an external AffineTransform, the filename of the image to paint and a fill factor of the image.
	 * @param at The AffineTransform to use internally.
	 * @param filename The filename of the image file to use.
	 * @param fillFactor The ratio of the desired average radius of the image to the actual average of the image's width and height.
	 */
	public ImagePaintStrategy(AffineTransform at, String filename, double fillFactor) {
		super(at);
		try {
			image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images" + filename));
		} catch (Exception e) {
			System.err.println("ImagePaintStrategy: Error reading file: " + filename + "\n" + e);
		}
	}

	/**
	 * Constructor that takes the image filename and fill factor.
	 * An AffineTransform is instantiated for internal use.
	 * GIF, JPG, PNG and any other file that can be loaded by an ImageIcon object can be used,
	 * including animated GIF's as the above files are.
	 * @param filename The filename of the image file to use.
	 * @param fillFactor The ratio of the desired average radius of the image to the actual average of the image's width and height.
	 */
	protected ImagePaintStrategy(String filename, double fillFactor) {
		super(new AffineTransform());
		try {
			image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/" + filename));
		} catch (Exception e) {
			System.err.println("ImagePaintStrategy: Error reading file: " + filename + "\n" + e);
		}

	}

	/**
	 * Draws the image on the given Graphics context using the given affine transform in combination with the local affine transform.
	 * @param g The graphics context to paint on.
	 * @param host The host Ball.
	 * @param at The AffineTransform to use.
	 */
	@Override
	public void paintXfrm(Graphics g, Ball host, AffineTransform at) {
		localAT.setToScale(scaleFactor, scaleFactor);
		localAT.translate(-image.getWidth(imageObs) / 2.0, -image.getHeight(imageObs) / 2.0);
		localAT.preConcatenate(at);
		((Graphics2D) g).drawImage(image, localAT, imageObs);
	}

	/**
	 * Initializes the internal ImageObserver reference from the host Ball.
	 * Also calculates the net scale factor for the image.
	 * @param host The host Ball.
	 */
	@Override
	public void init(Ball host) {
		imageObs = host.getContainer();
		MediaTracker mt = new MediaTracker(host.getContainer());
		mt.addImage(image, 1);
		try {
			mt.waitForAll();
		} catch (Exception e) {
			System.out.println("ImagePaintStrategy.init(): Error waiting for image.  Exception = " + e);
		}
		scaleFactor = 2.0 / (fillFactor * (image.getWidth(imageObs) + image.getHeight(imageObs)) / 2.0);
	}
}
