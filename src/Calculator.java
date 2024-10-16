class Calculator 
{
    public double Calculate(double value1, double value2, char operation)
    {
        switch (operation)
        {
            case '+':
            return value1 + value2;

            case '-':
            return value1 - value2;

            case '*':
            return value1 * value2;

            case '/':
            if (value2 != 0)
            {
                return value1 / value2;
            }

            default:
            return 0;
        }
    }

    public double Power(double value, int exponent)
    {
        double result = value;
        for (int i = 0; i < exponent; i++)
        {
            result *= value;
        }

        return result;
    }
}