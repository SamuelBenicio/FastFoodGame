package base;

public class Util {

    public static boolean colide(Elemento a, Elemento b) {
        if (!a.isAtivo() || !b.isAtivo()) //Verifica se os dois elementos estao ativos
            return false;

        // Definir os limites da tela, altura e largura
        final int xa = 0;
        final int ya = 0;
        final int xb = 448;
        final int yb = 0;
        final int xc = 448;
        final int yc = 550;
        final int xd = 0;
        final int yd = 550;

        // Coordenadas dos elementos
        final int xr1 = a.getPx();
        final int yr1 = a.getPy();
        final int xr2 = b.getPx();
        final int yr2 = b.getPy();

        // Verificar se ambos os elementos estão dentro da tela, se um estiver fora nao ocorre colisao
        if (!(xa <= xr1 && xr1 <= xb && ya <= yr1 && yr1 <= yd && xa <= xr2 && xr2 <= xb && ya <= yr2 && yr2 <= yd)) {
            return false;
        }

        // Verificar colisão no eixo X
        final int plA = a.getPx() + a.getLargura();
        final int plB = b.getPx() + b.getLargura();
        boolean colideX = a.getPx() < plB && plA > b.getPx();

        // Verificar colisão no eixo Y
        final int paA = a.getPy() + a.getAltura();
        final int paB = b.getPy() + b.getAltura();
        boolean colideY = a.getPy() < paB && paA > b.getPy();

        return colideX && colideY;
    }

    public static boolean colideX(Elemento a, Elemento b) {
        if (!a.isAtivo() || !b.isAtivo())
            return false;

        if (a.getPx() + a.getLargura() >= b.getPx() && a.getPx() <= b.getPx() + b.getLargura()) {
            return true;
        }

        return false;
    }

    public static void centraliza(Elemento el, int larg, int alt) {
        if (alt > 0)
            el.setPy(alt / 2 - el.getAltura() / 2);

        if (larg > 0)
            el.setPx(larg / 2 - el.getLargura() / 2);

    }

    public static void centraliza(Elemento el, Elemento elReferente) {
        el.setPx(elReferente.getPx() + elReferente.getLargura() / 2 - el.getLargura() / 2);
        el.setPy(elReferente.getPy() + elReferente.getAltura() / 2 - el.getAltura() / 2);
    }

    public static boolean saiu(Elemento e, int largura, int altura) {
        return saiu(e, largura, altura, 0);
    }

    public static boolean saiu(Elemento e, int largura, int altura, int margem) {
        if (e.getPx() < -margem || e.getPx() + e.getLargura() > largura + margem)
            return true;

        if (e.getPy() < -margem || e.getPy() + e.getAltura() > altura + margem)
            return true;

        return false;
    }

    public static void corrigePosicao(Elemento el, int limiteX, int limitY) {
        float nx = el.getMovPx(); 
        float ny = el.getMovPy(); 

        if (nx + el.getLargura() < 0)
            nx = limiteX;
        else if (nx > limiteX)
            nx = -el.getLargura();

        if (ny + el.getAltura() < 0)
            ny = limitY;
        else if (ny > limitY)
            ny = -el.getAltura();

        el.setPx(nx);
        el.setPy(ny);
    }
}
