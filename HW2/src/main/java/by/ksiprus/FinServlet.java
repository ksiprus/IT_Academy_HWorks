package by.ksiprus;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

@WebServlet(urlPatterns = "/fin")
public class FinServlet extends HttpServlet {
    /**
     * Обрабатывает GET-запросы для выполнения расчетов усреднения акций на основе данных, предоставленных пользователем.
     * Метод получает входные параметры (пары цена-количество), переданные через запрос,
     * вычисляет общую сумму покупки, общее количество и среднюю цену,
     * и формирует HTML-ответ с таблицей, отображающей результаты.
     *
     * @param req  объект HttpServletRequest, который содержит запрос, сделанный клиентом к сервлету
     * @param resp объект HttpServletResponse, который содержит ответ, отправляемый сервлетом клиенту
     * @throws ServletException если запрос не может быть обработан
     * @throws IOException      если происходит ошибка ввода-вывода при обработке GET-запроса сервлетом
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] rows = req.getParameterValues("rows");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();

        long totalPurchasePrice = 0;
        long totalQuantity = 0;

        // Основная таблица
        writer.write("<table border='1' bordercolor='green' width='400px' style='margin: 20px auto;'>");
        writer.write("<caption style='color: orange; font-weight: bold; font-size: 1.2em;'>Калькулятор усреднения акций</caption>");
        writer.write("<tr><th align='center'>Цена</th><th align='center'>Количество</th><th align='center'>Сумма</th></tr>");

        if (rows != null) {
            for (String row : rows) {
                String[] cells = row.split(",");
                if (cells.length >= 2) {
                    String priceStr = cells[0].trim();
                    String quantityStr = cells[1].trim();
                    try {
                        long price = Long.parseLong(priceStr);
                        long quantity = Long.parseLong(quantityStr);
                        long sumForRow = price * quantity;
                        totalPurchasePrice += sumForRow;
                        totalQuantity += quantity;

                        writer.write("<tr>");
                        writer.write("<td align='center'>" + priceStr + "</td>");
                        writer.write("<td align='center'>" + quantityStr + "</td>");
                        writer.write("<td align='center'>" + sumForRow + "</td>");
                        writer.write("</tr>");
                    } catch (NumberFormatException e) {
                        writer.write("<tr><td colspan='3' align='center'>Ошибка данных в строке: " + row + "</td></tr>");
                    }
                } else {
                    writer.write("<tr><td colspan='3' align='center'>Некорректные данные в строке: " + row + "</td></tr>");
                }
            }
        } else {
            writer.write("<tr><td colspan='3' align='center'>Нет данных для отображения.</td></tr>");
        }

        writer.write("</table>");

        // Вычисление средней цены
        double averagePrice = totalQuantity > 0 ? (double) totalPurchasePrice / totalQuantity : 0;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setMinimumFractionDigits(2); // Гарантирует два знака после запятой, даже если они нули
        df.setMaximumFractionDigits(2);

        // Дополнительная таблица для итоговых значений
        writer.write("<table border='1' bordercolor='green' width='400px' style='margin: 20px auto;'>");
        writer.write("<tr><td style='padding-left: 7px;'>Средняя цена покупки:</td><td align='right' style='padding-right: 7px;'>" + df.format(averagePrice) + "</td></tr>");
        writer.write("<tr><td style='padding-left: 7px;'>Общая сумма покупки:</td><td align='right' style='padding-right: 7px;'>" + totalPurchasePrice + "</td></tr>");
        writer.write("<tr><td style='padding-left: 7px;'>Общее количество:</td><td align='right' style='padding-right: 7 px;'>" + totalQuantity + "</td></tr>");
        writer.write("</table>");
    }
}