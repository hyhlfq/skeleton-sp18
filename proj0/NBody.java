public class NBody {

    public static String background = "./images/starfield.jpg";

    public static double readRadius(String fileName) {
        In in = new In(fileName);
        in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String fileName) {
        In in = new In(fileName);
        int num = in.readInt();
        in.readDouble();
        Planet[] planets = new Planet[num];
        for (int i = 0; i < num; i++) {
            planets[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
        }
        return planets;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String fileName = args[2];
        double radius = readRadius(fileName);
        Planet[] planets = readPlanets(fileName);

        int planetNum = planets.length;
        double xForces[] = new double[planetNum];
        double yForces[] = new double[planetNum];

        StdAudio.play("./audio/2001.mid");
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius, radius);

        for (double t = 0; t < T; t += dt) {
            for (int i = 0; i < planetNum; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            StdDraw.picture(-0, 0, background);
            for (int i = 0; i < planetNum; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
                StdDraw.picture(planets[i].xxPos, planets[i].yyPos, "./images/" + planets[i].imgFileName);
            }
            StdDraw.show();
            StdDraw.pause(10);
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                          planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
