/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.computec.view;

/**
 *
 * @author rulz
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    // Componentes de la interfaz
    private JPanel mainPanel;
    private JMenuBar menuBar;
    private JMenu menuClientes;
    private JMenu menuEquipos;
    private JMenu menuVentas;
    private JMenu menuReportes;
    private JMenuItem itemRegistrarCliente;
    private JMenuItem itemListarClientes;
    private JMenuItem itemRegistrarDesktop;
    private JMenuItem itemRegistrarLaptop;
    private JMenuItem itemListarEquipos;
    private JMenuItem itemRegistrarVenta;
    private JMenuItem itemListarVentas;
    private JMenuItem itemReporteEquiposVendidos;
    private JMenuItem itemReporteVentasTotales;
    private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private JPanel panelBienvenida;
    private JButton btnRegistrarCliente;
    private JButton btnRegistrarEquipo;
    private JButton btnRegistrarVenta;
    private JButton btnVerReportes;

    public MainFrame() {
        initComponents();
        setupFrame();
        configurarActionListeners(); // Movido aquí para que todos los componentes estén inicializados
    }

    private void initComponents() {
        // Configuración principal del frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema Computec - Control de Ventas");
        setResizable(true);

        // Crear panel principal
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        // Crear barra de menú
        crearMenuBar();

        // Crear panel de bienvenida
        crearPanelBienvenida();

        // Agregar componentes al frame
        setJMenuBar(menuBar);
        add(mainPanel);
    }

    private void crearMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(51, 102, 153));
        menuBar.setForeground(Color.WHITE);

        // Menú Clientes
        menuClientes = new JMenu("Clientes");
        menuClientes.setForeground(Color.WHITE);
        menuClientes.setFont(new Font("Segoe UI", Font.BOLD, 12));

        itemRegistrarCliente = new JMenuItem("Registrar Cliente");
        itemListarClientes = new JMenuItem("Listar Clientes");

        menuClientes.add(itemRegistrarCliente);
        menuClientes.add(itemListarClientes);

        // Menú Equipos
        menuEquipos = new JMenu("Equipos");
        menuEquipos.setForeground(Color.WHITE);
        menuEquipos.setFont(new Font("Segoe UI", Font.BOLD, 12));

        itemRegistrarDesktop = new JMenuItem("Registrar Desktop");
        itemRegistrarLaptop = new JMenuItem("Registrar Laptop");
        itemListarEquipos = new JMenuItem("Listar Equipos");

        menuEquipos.add(itemRegistrarDesktop);
        menuEquipos.add(itemRegistrarLaptop);
        menuEquipos.addSeparator();
        menuEquipos.add(itemListarEquipos);

        // Menú Ventas
        menuVentas = new JMenu("Ventas");
        menuVentas.setForeground(Color.WHITE);
        menuVentas.setFont(new Font("Segoe UI", Font.BOLD, 12));

        itemRegistrarVenta = new JMenuItem("Registrar Venta");
        itemListarVentas = new JMenuItem("Listar Ventas");

        menuVentas.add(itemRegistrarVenta);
        menuVentas.add(itemListarVentas);

        // Menú Reportes
        menuReportes = new JMenu("Reportes");
        menuReportes.setForeground(Color.WHITE);
        menuReportes.setFont(new Font("Segoe UI", Font.BOLD, 12));

        itemReporteEquiposVendidos = new JMenuItem("Equipos Vendidos");
        itemReporteVentasTotales = new JMenuItem("Ventas Totales");

        menuReportes.add(itemReporteEquiposVendidos);
        menuReportes.add(itemReporteVentasTotales);

        // Agregar menús a la barra
        menuBar.add(menuClientes);
        menuBar.add(menuEquipos);
        menuBar.add(menuVentas);
        menuBar.add(menuReportes);
    }

    private void crearPanelBienvenida() {
        panelBienvenida = new JPanel();
        panelBienvenida.setLayout(new BorderLayout());
        panelBienvenida.setBackground(new Color(240, 240, 240));
        panelBienvenida.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Panel superior con título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        panelTitulo.setBackground(new Color(240, 240, 240));

        lblTitulo = new JLabel("Sistema Computec");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(51, 102, 153));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblSubtitulo = new JLabel("Control de Ventas de Equipos Computacionales");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblSubtitulo.setForeground(new Color(100, 100, 100));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelTitulo.add(lblTitulo);
        panelTitulo.add(Box.createRigidArea(new Dimension(0, 10)));
        panelTitulo.add(lblSubtitulo);
        panelTitulo.add(Box.createRigidArea(new Dimension(0, 50)));

        // Panel de botones principales
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(2, 2, 20, 20));
        panelBotones.setBackground(new Color(240, 240, 240));
        panelBotones.setMaximumSize(new Dimension(600, 200));

        // Crear botones con estilo
        btnRegistrarCliente = crearBoton("Registrar Cliente", new Color(76, 175, 80));
        btnRegistrarEquipo = crearBoton("Registrar Equipo", new Color(33, 150, 243));
        btnRegistrarVenta = crearBoton("Registrar Venta", new Color(255, 152, 0));
        btnVerReportes = crearBoton("Ver Reportes", new Color(156, 39, 176));

        panelBotones.add(btnRegistrarCliente);
        panelBotones.add(btnRegistrarEquipo);
        panelBotones.add(btnRegistrarVenta);
        panelBotones.add(btnVerReportes);

        // Panel contenedor para centrar los botones
        JPanel panelBotonesCentrado = new JPanel();
        panelBotonesCentrado.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelBotonesCentrado.setBackground(new Color(240, 240, 240));
        panelBotonesCentrado.add(panelBotones);

        // Agregar todo al panel de bienvenida
        panelBienvenida.add(panelTitulo, BorderLayout.NORTH);
        panelBienvenida.add(panelBotonesCentrado, BorderLayout.CENTER);

        // Agregar panel de bienvenida al panel principal
        mainPanel.add(panelBienvenida, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(color.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(color);
            }
        });

        return boton;
    }

    private void configurarActionListeners() {
        // Verificar que los componentes estén inicializados
        if (btnRegistrarCliente == null) {
            System.err.println("Error: Los componentes de la interfaz no están inicializados correctamente");
            return;
        }

        // Action Listeners para menú Clientes
        itemRegistrarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRegistroCliente();
            }
        });

        itemListarClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirListadoClientes();
            }
        });

        // Action Listeners para menú Equipos
        itemRegistrarDesktop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRegistroDesktop();
            }
        });

        itemRegistrarLaptop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRegistroLaptop();
            }
        });

        itemListarEquipos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirListadoEquipos();
            }
        });

        // Action Listeners para menú Ventas
        itemRegistrarVenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRegistroVenta();
            }
        });

        itemListarVentas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirListadoVentas();
            }
        });

        // Action Listeners para menú Reportes
        itemReporteEquiposVendidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirReporteEquiposVendidos();
            }
        });

        itemReporteVentasTotales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirReporteVentasTotales();
            }
        });

        // Action Listeners para botones principales
        btnRegistrarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRegistroCliente();
            }
        });

        btnRegistrarEquipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mostrar diálogo para elegir tipo de equipo
                Object[] opciones = {"Desktop", "Laptop"};
                int seleccion = JOptionPane.showOptionDialog(
                    MainFrame.this,
                    "Seleccione el tipo de equipo a registrar:",
                    "Registrar Equipo",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
                );

                if (seleccion == 0) {
                    abrirRegistroDesktop();
                } else if (seleccion == 1) {
                    abrirRegistroLaptop();
                }
            }
        });

        btnRegistrarVenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRegistroVenta();
            }
        });

        btnVerReportes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mostrar diálogo para elegir reporte
                Object[] opciones = {"Equipos Vendidos", "Ventas Totales"};
                int seleccion = JOptionPane.showOptionDialog(
                    MainFrame.this,
                    "Seleccione el reporte a visualizar:",
                    "Ver Reportes",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
                );

                if (seleccion == 0) {
                    abrirReporteEquiposVendidos();
                } else if (seleccion == 1) {
                    abrirReporteVentasTotales();
                }
            }
        });
    }

    private void setupFrame() {
        setSize(900, 600);
        setLocationRelativeTo(null); // Centrar en la pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Establecer icono (opcional)
        try {
            // Puedes agregar un icono aquí si tienes uno
            // setIconImage(Toolkit.getDefaultToolkit().getImage("icono.png"));
        } catch (Exception e) {
            System.out.println("No se pudo cargar el icono: " + e.getMessage());
        }
    }

    // Métodos para abrir los diferentes formularios
    private void abrirRegistroCliente() {
        SwingUtilities.invokeLater(() -> {
            RegistroClienteForm form = new RegistroClienteForm(this);
            form.setVisible(true);
        });
    }

    private void abrirListadoClientes() {
        SwingUtilities.invokeLater(() -> {
            ListadoClientesForm form = new ListadoClientesForm(this);
            form.setVisible(true);
        });
    }

    private void abrirRegistroDesktop() {
        SwingUtilities.invokeLater(() -> {
            RegistroEquipoForm form = new RegistroEquipoForm(this, "DESKTOP");
            form.setVisible(true);
        });
    }

    private void abrirRegistroLaptop() {
        SwingUtilities.invokeLater(() -> {
            RegistroEquipoForm form = new RegistroEquipoForm(this, "LAPTOP");
            form.setVisible(true);
        });
    }

    private void abrirListadoEquipos() {
        SwingUtilities.invokeLater(() -> {
            ListadoEquiposForm form = new ListadoEquiposForm(this);
            form.setVisible(true);
        });
    }

    private void abrirRegistroVenta() {
        SwingUtilities.invokeLater(() -> {
            RegistroVentaForm form = new RegistroVentaForm(this);
            form.setVisible(true);
        });
    }

    private void abrirListadoVentas() {
        SwingUtilities.invokeLater(() -> {
            ListadoVentasForm form = new ListadoVentasForm(this);
            form.setVisible(true);
        });
    }

    private void abrirReporteEquiposVendidos() {
        SwingUtilities.invokeLater(() -> {
            ReporteEquiposVendidosForm form = new ReporteEquiposVendidosForm(this);
            form.setVisible(true);
        });
    }

    private void abrirReporteVentasTotales() {
        SwingUtilities.invokeLater(() -> {
            ReporteVentasTotalesForm form = new ReporteVentasTotalesForm(this);
            form.setVisible(true);
        });
    }

    // Método principal para ejecutar la aplicación
    public static void main(String[] args) {
        // Establecer el look and feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error al establecer el Look and Feel: " + e.getMessage());
        }

        // Ejecutar la aplicación en el Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
                System.out.println("Aplicación iniciada correctamente");
            }
        });
    }

    // Método para mostrar mensajes de información
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    // Método para mostrar mensajes de error
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Método para confirmar acciones
    public boolean confirmarAccion(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmar", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }
}