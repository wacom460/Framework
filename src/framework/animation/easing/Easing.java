package framework.animation.easing;

public class Easing {

	public static double apply(EaseType type, double val, double start, double end){
		return apply(type, val, start, end, end);
	}

	private static double apply(EaseType type, double time, double initial, double change, double duration)
	{
		if (change == 0 || time == 0 || duration == 0) return initial;
		if (time == duration) return initial + change;

		final float pi = 3.14159274f;
		switch (type)
		{
		case None:
			return time;
		case In:
			return change * (time /= duration) * time + initial;
		case Out:
			return -change * (time /= duration) * (time - 2) + initial;
		case InOut:
			if ((time /= duration / 2) < 1) return change / 2 * time * time + initial;
			return -change / 2 * ((--time) * (time - 2) - 1) + initial;
		case InCubic:
			return change * (time /= duration) * time * time + initial;
		case OutCubic:
			return change * ((time = time / duration - 1) * time * time + 1) + initial;
		case InOutCubic:
			if ((time /= duration / 2) < 1) return change / 2 * time * time * time + initial;
			return change / 2 * ((time -= 2) * time * time + 2) + initial;
		case InQuart:
			return change * (time /= duration) * time * time * time + initial;
		case OutQuart:
			return -change * ((time = time / duration - 1) * time * time * time - 1) + initial;
		case InOutQuart:
			if ((time /= duration / 2) < 1) return change / 2 * time * time * time * time + initial;
			return -change / 2 * ((time -= 2) * time * time * time - 2) + initial;
		case InQuint:
			return change * (time /= duration) * time * time * time * time + initial;
		case OutQuint:
			return change * ((time = time / duration - 1) * time * time * time * time + 1) + initial;
		case InOutQuint:
			if ((time /= duration / 2) < 1) return change / 2 * time * time * time * time * time + initial;
			return change / 2 * ((time -= 2) * time * time * time * time + 2) + initial;
		case InSine:
			return -change * Math.cos(time / duration * (pi / 2)) + change + initial;
		case OutSine:
			return change * Math.sin(time / duration * (pi / 2)) + initial;
		case InOutSine:
			return -change / 2 * (Math.cos(pi * time / duration) - 1) + initial;
		case InExpo:
			return change * Math.pow(2, 10 * (time / duration - 1)) + initial;
		case OutExpo:
			return (time == duration) ? initial + change : change * (-Math.pow(2, -10 * time / duration) + 1) + initial;
		case InOutExpo:
			if ((time /= duration / 2) < 1) return change / 2 * Math.pow(2, 10 * (time - 1)) + initial;
			return change / 2 * (-Math.pow(2, -10 * --time) + 2) + initial;
		case InCirc:
			return -change * (Math.sqrt(1 - (time /= duration) * time) - 1) + initial;
		case OutCirc:
			return change * Math.sqrt(1 - (time = time / duration - 1) * time) + initial;
		case InOutCirc:
			if ((time /= duration / 2) < 1) return -change / 2 * (Math.sqrt(1 - time * time) - 1) + initial;
			return change / 2 * (Math.sqrt(1 - (time -= 2) * time) + 1) + initial;
		case InElastic:
		{
			if ((time /= duration) == 1) return initial + change;

			double p = duration * 0.3;
			double a = change;
			double s = 1.70158;
			if (a < Math.abs(change)) { a = change; s = p / 4; }
			else s = p / (2 * pi) * Math.asin(change / a);
			return -(a * Math.pow(2, 10 * (time -= 1)) * Math.sin((time * duration - s) * (2 * pi) / p)) + initial;
		}
		case OutElastic:
		{
			if ((time /= duration) == 1) return initial + change;

			double p = duration * 0.3;
			double a = change;
			double s = 1.70158;
			if (a < Math.abs(change)) { a = change; s = p / 4; }
			else s = p / (2 * pi) * Math.asin(change / a);
			return a * Math.pow(2, -10 * time) * Math.sin((time * duration - s) * (2 * pi) / p) + change + initial;
		}
		case OutElasticHalf:
		{
			if ((time /= duration) == 1) return initial + change;

			double p = duration * 0.3;
			double a = change;
			double s = 1.70158;
			if (a < Math.abs(change)) { a = change; s = p / 4; }
			else s = p / (2 * pi) * Math.asin(change / a);
			return a * Math.pow(2, -10 * time) * Math.sin((0.5f * time * duration - s) * (2 * pi) / p) + change + initial;
		}
		case OutElasticQuarter:
		{
			if ((time /= duration) == 1) return initial + change;

			double p = duration * 0.3;
			double a = change;
			double s = 1.70158;
			if (a < Math.abs(change)) { a = change; s = p / 4; }
			else s = p / (2 * pi) * Math.asin(change / a);
			return a * Math.pow(2, -10 * time) * Math.sin((0.25f * time * duration - s) * (2 * pi) / p) + change + initial;
		}
		case InOutElastic:
		{
			if ((time /= duration / 2) == 2) return initial + change;

			double p = duration * (0.3 * 1.5);
			double a = change;
			double s = 1.70158;
			if (a < Math.abs(change)) { a = change; s = p / 4; }
			else s = p / (2 * pi) * Math.asin(change / a);
			if (time < 1) return -0.5 * (a * Math.pow(2, 10 * (time -= 1)) * Math.sin((time * duration - s) * (2 * pi) / p)) + initial;
			return a * Math.pow(2, -10 * (time -= 1)) * Math.sin((time * duration - s) * (2 * pi) / p) * 0.5 + change + initial;
		}
		case InBack:
		{
			double s = 1.70158;
			return change * (time /= duration) * time * ((s + 1) * time - s) + initial;
		}
		case OutBack:
		{
			double s = 1.70158;
			return change * ((time = time / duration - 1) * time * ((s + 1) * time + s) + 1) + initial;
		}
		case InOutBack:
		{
			double s = 1.70158;
			if ((time /= duration / 2) < 1) return change / 2 * (time * time * (((s *= (1.525)) + 1) * time - s)) + initial;
			return change / 2 * ((time -= 2) * time * (((s *= (1.525)) + 1) * time + s) + 2) + initial;
		}
		case InBounce:
			return change - apply(EaseType.OutBounce, duration - time, 0, change, duration) + initial;
		case OutBounce:
			if ((time /= duration) < (1 / 2.75))
			{
				return change * (7.5625 * time * time) + initial;
			}
			else if (time < (2 / 2.75))
			{
				return change * (7.5625 * (time -= (1.5 / 2.75)) * time + 0.75) + initial;
			}
			else if (time < (2.5 / 2.75))
			{
				return change * (7.5625 * (time -= (2.25 / 2.75)) * time + 0.9375) + initial;
			}
			else
			{
				return change * (7.5625 * (time -= (2.625 / 2.75)) * time + 0.984375) + initial;
			}
		case InOutBounce:
			if (time < duration / 2) return apply(EaseType.InBounce, time * 2, 0, change, duration) * 0.5 + initial;
			return apply(EaseType.OutBounce, time * 2 - duration, 0, change, duration) * 0.5 + change * 0.5 + initial;
		default:
				return change * (time / duration) + initial;
		}
	}
}
