package net.leomixer17.pluginlib.nbt;

public class NBTContainer extends NBTCompound {

    private Object nbt;

    public NBTContainer()
    {
        super(null, null);
        nbt = NBTReflectionUtil.getNewNBTTag();
    }

    protected NBTContainer(Object nbt)
    {
        super(null, null);
        this.nbt = nbt;
    }

    public NBTContainer(String nbtString) throws IllegalArgumentException
    {
        super(null, null);
        try
        {
            this.nbt = NBTReflectionUtil.parseNBT(nbtString);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new IllegalArgumentException("Malformed Json: " + e.getMessage());
        }
    }

    protected Object getCompound()
    {
        return nbt;
    }

    protected void setCompound(Object tag)
    {
        this.nbt = tag;
    }

}
