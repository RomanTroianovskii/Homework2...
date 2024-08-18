

public class Main2 {
    public static void main(String[] args) {

        // Создаём объекты типа Product
        Product first = new Product("carrot", 1.5, 2);
        Product second = new Product("potato", 3, 3);
        Product third = new Product("egg", 2, 4);


        ShoppingCart cart0 = new ShoppingCart();
        cart0.addProduct(first);
        cart0.addProduct(second);
        cart0.addProduct(third);

        System.out.println(cart0.calculateTotal()); //Просто провека без изменений продуктов и т.д (1.5 * 2 + 3 * 3 + 4 * 2 = 20) (out: 20.0)

        first.setPrice(1);
        second.setName("potato_new");
        third.setQuantity(2);

        ShoppingCart cart1 = new ShoppingCart();

        cart1.addProduct(first); //Проверка метода addProduct
        cart1.addProduct(second);
        cart1.addProduct(third);

        cart1.removeProduct(second);  // Проверка метода removeProduct
        System.out.println(cart1.calculateTotal()); // Проверка результата - (1.0 * 2.0 + 2.0 * 2.0 = 6.0) (out: 6.0)
        System.out.println(cart1.calculateTotal()); // Проверка обнуления (out: 0.0)

        ShoppingCart cart2 = new ShoppingCart(); //Проверка пустого ShoppingCart
        System.out.println(cart2.calculateTotal()); // Пустой ShoppingCart (out 0.0)

        ShoppingCart cart3 = new ShoppingCart();

        cart3.addProduct(first); //Проверка добавления двух одинаовых компонентов
        cart3.addProduct(first);

        cart3.removeProduct(new Product("carrot", 1, 2)); //1. Должен вызвать Exception из-за различия ссылок
        cart3.removeProduct(first);  //Должен удалить ТОЛЬКО 1 first
        System.out.println(cart3.calculateTotal()); // Проверка результата - (1.0 * 2.0 = 2.0) (out: 2.0)

    }
}
class Product {
    private String name = "";
    private double price = 0.0;
    private int quantity = 0;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    //#region Get_and_Set


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAllPrice() {
        return getPrice() * getQuantity();
    }

    //#endregion

}

class ShoppingCart
{
    private final DynamicArray<Product> list = new DynamicArray<Product>(DynamicArray.INFINITY_LENGTH);
    public void addProduct(Product product)
    {
        try {
            list.add(product);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

    }

    public void removeProduct(Product product)
    {
        try
        {
            list.delete(product);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public double calculateTotal()
    {
        double result = 0;
        for (int i = 0; i < list.getCurrentLength(); i++) {
            result += list.getItem(i).getAllPrice();
        }
        list.zeroAll();
        return result;
    }

}

class DynamicArray<E>
{
    public static int INFINITY_LENGTH = -1;
    private int maxLength = 256;
    private  int currentLength = 0;
    private  int currentIndexToAdd = 0;

    private Object[] items = new Object[0];
    private Object[] temp = new Object[0];

    public DynamicArray(int maxLength) {
        this.maxLength = maxLength;
    }

    public DynamicArray(int maxLength, E firstItem) {
        this.maxLength = maxLength;
        try
        {
            add(firstItem);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

    }

    public DynamicArray(){};


    public int getCurrentLength() {
        return currentLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public void add(E item) throws Exception {
    if(maxLength != INFINITY_LENGTH)
    {
        if(currentLength > maxLength)
        {
            throw new Exception("There is no place for the element in the array!");
        }
    }
        temp = new Object[currentLength];
        temp = items;
        items = new Object[++currentLength];
        for(int i = 0; i <  currentLength - 1; i++) {
            items[i] = temp[i];
        }
        items[currentIndexToAdd++] = item;
    }


    public void delete(E item) throws Exception
    {
        int itemToDeleteIndex = 0;
        boolean isExistItemToDelete = false;
        for (int i = 0; i < currentLength; i++)
        {
            if( items[i].equals(item))
            {
                itemToDeleteIndex = i;
                isExistItemToDelete = true;
            }
        }
        if(!isExistItemToDelete)
        {
            throw new Exception("item is not exist! At method DynamicArray<E>.delete(E item)");
        }
        else
        {
            for (int i = itemToDeleteIndex; i < currentLength - 1; i++)
            {
                items[i] = items[i + 1];
            }
        }
        temp = new Object[currentLength];
        temp = items;
        items = new Object[--currentLength];
        for(int i = 0; i <  currentLength; i++) {
            items[i] = temp[i];
        }
        System.out.println("1");
    }

    public void zeroAll()
    {
        currentLength = 0;
        currentIndexToAdd = 0;
        items = new Object[0];
        temp = new Object[0];
    }
    @SuppressWarnings("unchecked")
    public E getItem(int index)
    {
        return (E)items[index];
    }
}
