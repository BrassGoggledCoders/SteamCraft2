/**
 * This class was created by <Surseance> or his SC2 development team. 
 * This class is available as part of the Steamcraft 2 Mod for Minecraft.
 *
 * Steamcraft 2 is open-source and is distributed under the MMPL v1.0 License.
 * (http://www.mod-buildcraft.com/MMPL-1.0.txt)
 *
 * Steamcraft 2 is based on the original Steamcraft Mod created by Proloe.
 * Steamcraft (c) Proloe 2011
 * (http://www.minecraftforum.net/topic/251532-181-steamcraft-source-code-releasedmlv054wip/)
 *
 * File created @ [Apr 8, 2014, 1:20:21 PM]
 */
package steamcraft.common.lib.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import boilerplate.common.Utils;

/**
 * @author Surseance (Johnny Eatmon)
 * 
 */
public abstract class NetworkTile extends TileEntity
{
	/** */
	private boolean markedForResend;

	public boolean isMarkedForResend()
	{
		return markedForResend;
	}

	public void setMarkedForResend(final boolean markedForResend)
	{
		this.markedForResend = markedForResend;
	}

	public void sendPacket()
	{
		final Packet packet = getDescriptionPacket();
		// packet.isChunkDataPacket = false;
		Utils.sendToPlayers(packet, worldObj, xCoord, yCoord, zCoord, null);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		final DataOutputStream dataStream = new DataOutputStream(byteStream);

		try
		{
			dataStream.writeInt(10);

			dataStream.writeInt(xCoord);
			dataStream.writeInt(yCoord);
			dataStream.writeInt(zCoord);

			writePacket(dataStream);
		} catch (final IOException e)
		{
			e.printStackTrace();
		}

		/*
		 * Packet250CustomPayload packet = new Packet250CustomPayload();
		 * packet.channel = "SC2_Channel"; packet.data =
		 * byteStream.toByteArray(); packet.length = byteStream.size();
		 * packet.isChunkDataPacket = true;
		 */

		return null;// packet;
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if ((!worldObj.isRemote) && (isMarkedForResend()))
		{
			setMarkedForResend(false);
		}
	}

	public abstract void writePacket(DataOutputStream dataStream)
			throws IOException;

	public abstract void readPacket(DataInputStream dataStream)
			throws IOException;

	public abstract void readPacketFromClient(DataInputStream dataStream)
			throws IOException;
}
