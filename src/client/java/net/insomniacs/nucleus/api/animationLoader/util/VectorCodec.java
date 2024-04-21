package net.insomniacs.nucleus.api.animationLoader.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.util.Util;
import org.joml.Vector3f;

import java.util.List;

public class VectorCodec {

	public static final Vector3f ZERO = new Vector3f(0);

	public static final Codec<Vector3f> CODEC = Codec.FLOAT.listOf().comapFlatMap(VectorCodec::toVec, VectorCodec::fromVec);

	private static DataResult<Vector3f> toVec(List<Float> val) {
		return Util.decodeFixedLengthList(val, 3).map(
				list -> new Vector3f(list.get(0), list.get(1), list.get(2))
		);
	}

	private static List<Float> fromVec(Vector3f vec) {
		return List.of(vec.x, vec.y, vec.z);
	}

}
