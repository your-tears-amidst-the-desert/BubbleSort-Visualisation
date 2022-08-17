import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class VisualSort extends JPanel {
        //список объектов
        private List<Object> shapes = new ArrayList<>();
        private Random random = new Random();
        //размеры окна
        final int HEIGHT = 400;
        final int WIDTH = 800;

        //конструктор класса, он добавляет объекты на экран
        public VisualSort(int amount, String type, int[] mas) {
                setBackground(Color.BLACK);
                setPreferredSize(new Dimension(WIDTH, HEIGHT));

                switch(type) {
                        case"Прямоугольники":
                                int step1 = 0;
                                try {
                                        step1 = (WIDTH - 10) / amount;
                                }
                                catch (ArithmeticException ignored) {
                                }
                                for(int j = 0; j < amount; j++) {
                                        shapes.add(new Rectangle(10+(j)*step1,30, WIDTH/(amount+10),mas[j]*3));
                                        repaint();
                                }
                                break;
                        case"Круги":
                                int step2 = 0;
                                try {
                                        step2 = (WIDTH - 100) / amount;
                                }
                                catch (ArithmeticException ignored) {
                                }
                                for(int j = 0; j < amount; j++) {
                                        shapes.add(new Circle(10+j*step2, 20, (int)(mas[j]*1.5), (int)(mas[j]*1.5)));
                                        repaint();
                                }
                                break;
                }
        }

        //Метод paintComponent() вызывается при прорисовке компонента первым, и именно он рисует сам компонент
        @Override
        protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for(Object s : shapes) {
                        if(s instanceof Rectangle) {
                                ((Rectangle) s).draw(g);
                        }
                        else if (s instanceof Circle) {
                                ((Circle) s).draw(g);
                        }
                }
        }

        /* использовалось ранее, оказалось ненужным
        public void addRectangle() {
                shapes.add(new Rectangle(0,300, 10,10));
                repaint();
        }
        public void addCircle(int maxX, int maxY) {
                shapes.add(new Circle(random.nextInt(maxX), random.nextInt(maxY),10,10));
                repaint();
        }
        */

        //точка входа в программу, где запрашивается количество чисел, тип визуализации, происходит сортировка
        public static void main(String[] args) {
                String Amount = JOptionPane.showInputDialog(null,
                        "Сколько чисел?", "Сортировка пузырьком", JOptionPane.PLAIN_MESSAGE);
                int amount = 0;
                try {
                        amount = Integer.parseInt(Amount);
                } catch (NumberFormatException ignored) {
                }
                if (amount != 0) {
                        String[] type = {"Прямоугольники", "Круги"};
                        String Type = (String) JOptionPane.showInputDialog(null,
                                "Тип визуализации", "Сортировка пузырьком",
                                JOptionPane.PLAIN_MESSAGE, null, type, type[0]);

                        //SwingUtilities.invokeLater(() -> new VisualSort(amount, Type).setVisible(true));

                        Random rnd = new Random();
                        final int[] mas_user = new int[amount];
                        //заполняем массив случайными числами
                        for (int i = 0; i < mas_user.length; i++) {
                                mas_user[i] = rnd.nextInt(101);
                        }
                        int[] mas_sort = new int[amount];
                        for(int i = 0; i < amount; i++){
                                mas_sort[i] = mas_user[i];
                        }
                        System.out.println(Arrays.toString(mas_user) + " user");

                        //создаём фрэйм и закидываем в него элементы для отображения
                        JFrame frame = new JFrame();
                        Button dosort = new Button("Отсортировать");
                        Button do_sort = new Button("До сортировки");
                        Button menu = new Button("В меню");
                        Button next = new Button("Следующий шаг");
                        frame.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                        frame.setLayout(new FlowLayout());
                        frame.add(next);
                        frame.add(dosort);
                        frame.add(do_sort);
                        frame.add(menu);
                        Component do_sortirovki = null;
                        try {
                                do_sortirovki = new VisualSort(amount, Type, mas_user);
                        } catch (NullPointerException ignored) {
                        }
                        try {
                                frame.add(do_sortirovki);
                        } catch (NullPointerException ignored) {
                        }

                        //сама сортировка пузырьком
                        boolean isSorted = false;
                        int buf;
                        while (!isSorted) {
                                isSorted = true;
                                for (int i = 0; i < mas_sort.length - 1; i++) {
                                        if (mas_sort[i] > mas_sort[i+1]) {
                                                isSorted = false;

                                                buf = mas_sort[i];
                                                mas_sort[i] = mas_sort[i+1];
                                                mas_sort[i+1] = buf;
                                        }
                                }
                        }
                        Component posle_sortirovki = null;
                        try {
                                posle_sortirovki = new VisualSort(amount, Type, mas_sort);
                        } catch (NullPointerException ignored) {
                        }
                        int[] mas_step = new int[amount];
                        for(int i = 0; i < amount; i++){
                                mas_step[i] = mas_user[i];
                        }
                        System.out.println(Arrays.toString(mas_step) + " step");
                        final int famount = amount;

                        final Component[] step = {null};
                        try{
                                step[0] = new VisualSort(amount, Type, mas_step);
                        } catch (NullPointerException ignored) {
                        }
                        final Component[] finalstep = {step[0]};

                        //функции для кнопок
                        Component finalDo_sortirovki = do_sortirovki;
                        Component finalPosle_sortirovki = posle_sortirovki;
                        dosort.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                        try {
                                                frame.remove(finalDo_sortirovki);
                                                frame.remove(finalstep[0]);
                                                frame.add(finalPosle_sortirovki);
                                                frame.setSize(1200, 449);
                                        } catch (NullPointerException ignored) {
                                        }
                                }
                        });

                        Component finalDo_sortirovki1 = do_sortirovki;
                        Component finalPosle_sortirovki1 = posle_sortirovki;
                        do_sort.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                        try {
                                                frame.remove(finalPosle_sortirovki1);
                                                frame.remove(finalstep[0]);
                                                frame.add(finalDo_sortirovki1);
                                                frame.setSize(1200, 448);
                                        } catch (NullPointerException ignored) {
                                        }
                                }
                        });

                        menu.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                        main(args);
                                        frame.dispose();
                                }
                        });

                        Component finalDo_sortirovki2 = do_sortirovki;
                        Component finalPosle_sortirovki2 = posle_sortirovki;

                        next.addActionListener(new ActionListener() {
                                int count = 1; int j = 1;
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                        int buf;
                                        if(count < famount) {
                                                for (int i = count - 1; i < count; i++) {
                                                        if (mas_step[i] > mas_step[i + 1]) {

                                                                buf = mas_step[i];
                                                                mas_step[i] = mas_step[i + 1];
                                                                mas_step[i + 1] = buf;
                                                        }
                                                        if(count == famount - j) {
                                                                count = 0;
                                                                j++;
                                                        }

                                                        frame.remove(finalstep[0]);
                                                        try {
                                                                finalstep[0] = new VisualSort(famount, Type, mas_step);
                                                        } catch (NullPointerException ignored) {
                                                        }
                                                        frame.remove(finalDo_sortirovki2);
                                                        frame.remove(finalPosle_sortirovki2);
                                                        frame.add(finalstep[0]);
                                                        frame.setSize(1200, 443+count);
                                                }
                                        }
                                        count += 1;
                                }
                        });

                        //упаковываем и отображаем фрэйм, метод pack() подберет оптимальный размер окна с компонентами и т.п.
                        System.out.println(Arrays.toString(mas_sort) + " sort");
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                }
                else {
                        JOptionPane.showMessageDialog(null, "Вы либо ничего не ввели, либо ввели 0.","А-я-яй", JOptionPane.ERROR_MESSAGE);
                        main(args);
                }
        }
}
