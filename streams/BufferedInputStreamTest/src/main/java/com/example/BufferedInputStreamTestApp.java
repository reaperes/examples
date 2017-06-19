package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import java.io.*;

@SpringBootApplication
public class BufferedInputStreamTestApp implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		tryFirst();
		trySecond();
		tryThird();
		tryFourth();
		tryFifth();
		trySeventh();
		tryEighth();
		tryNinth();
		tryTenth();
	}

	private void tryFirst() throws IOException {
		File file = ResourceUtils.getFile("classpath:com/example/image.png");
		InputStream inputStream = new FileInputStream(file);
		assert !inputStream.markSupported();

		System.out.println("Original file is this");
		for (int i=0; i<10; i++) {
			System.out.print(inputStream.read());
			System.out.print(" ");
		}
		System.out.print("\n");

		inputStream.close();
	}

	private void trySecond() throws IOException {
		File file = ResourceUtils.getFile("classpath:com/example/image.png");
		InputStream stream = new FileInputStream(file);
		InputStream clone = new BufferedInputStream(stream);

		System.out.print(String.format(
			"%s %s %s ",
			stream.read(),
			stream.read(),
			stream.read()
		));

		System.out.print(String.format(
			"%s %s %s ",
			clone.read(),
			clone.read(),
			clone.read()
		));

		System.out.println("- BufferedInputStream uses original input stream pos");

		clone.close();
		stream.close();
	}

	private void tryThird() throws IOException {
		File file = ResourceUtils.getFile("classpath:com/example/image.png");
		InputStream stream = new FileInputStream(file);
		InputStream clone = new BufferedInputStream(stream);

		System.out.print(String.format(
			"%s %s %s ",
			clone.read(),
			clone.read(),
			clone.read()
		));

		System.out.print(String.format(
			"%s %s %s ",
			stream.read(),
			stream.read(),
			stream.read()
		));

		System.out.println("- InputStream does not know about BufferedInputStream");

		clone.close();
		stream.close();
	}

	private void tryFourth() throws IOException {
		File file = ResourceUtils.getFile("classpath:com/example/image.png");
		InputStream stream = new FileInputStream(file);
		InputStream clone = new BufferedInputStream(stream);
		assert !clone.markSupported();

		clone.mark(0);
		System.out.print(String.format(
			"%s %s %s ",
			clone.read(),
			clone.read(),
			clone.read()
		));
		clone.reset();

		clone.mark(0);
		System.out.print(String.format(
			"%s %s %s ",
			clone.read(),
			clone.read(),
			clone.read()
		));
		clone.reset();

		System.out.println("- If use mark, you can revert the pos");

		clone.close();
		stream.close();
	}

	private void tryFifth() throws IOException {
		File file = ResourceUtils.getFile("classpath:com/example/image.png");
		InputStream stream = new FileInputStream(file);
		InputStream clone = new BufferedInputStream(stream);
		assert !clone.markSupported();

		clone.mark(0);
		System.out.print(clone.read());
		System.out.print(" ");
		System.out.print(clone.read());
		System.out.print(" ");
		System.out.print(clone.read());
		System.out.print(" ");
		clone.reset();

		System.out.print(stream.read());
		System.out.print(" ");
		System.out.print(stream.read());
		System.out.print(" ");
		System.out.print(stream.read());
		System.out.print(" ");

		System.out.println("- BufferedInputStream read original input stream, then reset only affected on buffered input stream");

		stream.close();
	}

	private void trySeventh() throws IOException {
		InputStream stream = null;

		try {
			File file = ResourceUtils.getFile("classpath:com/example/image.png");
			stream = new FileInputStream(file);
			InputStream clone = new BufferedInputStream(stream, 2); // set buffer size small
			assert !clone.markSupported();

			clone.mark(0);
			System.out.print(clone.read());
			System.out.print(" ");
			System.out.print(clone.read());
			System.out.print(" ");
			System.out.print(clone.read());
			System.out.print(" ");
			clone.reset();

			System.out.print(clone.read());
			System.out.print(" ");
			System.out.print(clone.read());
			System.out.print(" ");
			System.out.print(clone.read());
			System.out.print(" ");
		} catch (Exception e) {
			System.out.println("- buffer size is small and read exceed mark limit, reset throws exception");
		} finally {
			stream.close();
		}
	}

	private void tryEighth() throws IOException {
		File file = ResourceUtils.getFile("classpath:com/example/image.png");
		InputStream stream = new FileInputStream(file);
		InputStream clone = new BufferedInputStream(stream, 2); // set buffer size small
		assert !clone.markSupported();

		clone.mark(3);
		System.out.print(clone.read());
		System.out.print(" ");
		System.out.print(clone.read());
		System.out.print(" ");
		System.out.print(clone.read());
		System.out.print(" ");
		clone.reset();

		System.out.print(clone.read());
		System.out.print(" ");
		System.out.print(clone.read());
		System.out.print(" ");
		System.out.print(clone.read());
		System.out.print(" ");

		System.out.println("- but mark is enough large, reset can do its job");

		stream.close();
	}

	private void tryNinth() throws IOException {
		File file = ResourceUtils.getFile("classpath:com/example/image.png");
		InputStream stream = new FileInputStream(file);
		InputStream clone = new BufferedInputStream(stream, 2); // set buffer size small
		assert !clone.markSupported();

		clone.mark(3);
		System.out.print(clone.read());
		System.out.print(" ");
		System.out.print(clone.read());
		System.out.print(" ");
		System.out.print(clone.read());
		System.out.print(" ");
		clone.reset();

		System.out.print(clone.read());
		System.out.print(" ");
		System.out.print(clone.read());
		System.out.print(" ");
		System.out.print(clone.read());
		System.out.print(" ");
		System.out.print(clone.read());
		System.out.print(" ");

		System.out.println("- after reset data streams well");

		stream.close();
	}

	private void tryTenth() throws IOException {
		InputStream stream = null;

		try {
			File file = ResourceUtils.getFile("classpath:com/example/image.png");
			stream = new FileInputStream(file);
			InputStream clone = new BufferedInputStream(stream, 2); // set buffer size small
			assert !clone.markSupported();

			clone.mark(3);
			System.out.print(clone.read());
			System.out.print(" ");
			System.out.print(clone.read());
			System.out.print(" ");
			System.out.print(clone.read());
			System.out.print(" ");
			System.out.print(clone.read());
			System.out.print(" ");
			clone.reset();

			System.out.print(clone.read());
			System.out.print(" ");
			System.out.print(clone.read());
			System.out.print(" ");
			System.out.print(clone.read());
			System.out.print(" ");
			System.out.print(clone.read());
			System.out.print(" ");
		} catch (Exception e) {
			System.out.println("- when flood to read more than mark size, reset does not work");
		}
		stream.close();
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BufferedInputStreamTestApp.class, args);
	}
}
