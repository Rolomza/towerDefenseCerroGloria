public class Coordenada {
    private int x;
    private int y;
    public Coordenada(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public String mostrarCoordenada() {
        return "(" + x + ", " + y + ")";
    }

    public boolean compararConCoordenada(Coordenada coordenada2){
        return this.getX() == coordenada2.getX() && this.getY() == coordenada2.getY();
    }


}