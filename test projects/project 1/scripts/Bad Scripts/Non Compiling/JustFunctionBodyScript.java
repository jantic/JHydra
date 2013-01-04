
final BigDecimal num1 = new BigDecimal(getValue("num1"));
final BigDecimal num2 = new BigDecimal(getValue("num2"));
final BigDecimal sum = num1.add(num2);
setValue("sum", sum.toString());


