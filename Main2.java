

public class Main2 {
    public static void main(String[] args) {
        Product first = new Product("carrot", 1.5, 2);
        Product second = new Product("potato", 3, 3);
        Product third = new Product("egg", 2, 4);

        first.setPrice(1);
        second.setName("potato_new");
        third.setQuantity(2);

        ShoppingCart cart = new ShoppingCart();

        cart.addProduct(first);
        cart.addProduct(second);
        cart.addProduct(third);

        cart.removeProduct(second);
        System.out.println(cart.calculateTotal());
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

    public int getQuantity()
    {
        return this.quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAllPrice()
    {
        return getPrice()*getQuantity();
    }
    //#endregion
}

class ShoppingCart
{
    private DynamicArray<Product> list = new DynamicArray<Product>();
    public void addProduct(Product product)
    {
        list.add(product);
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
        add(firstItem);
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

    public void add(E item)
    {
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
            if(items[i] == item)
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
