package com.github.grhscompsci2.galaga.ashley;

import com.badlogic.ashley.core.ComponentMapper;
import com.github.grhscompsci2.galaga.ashley.components.*;

/**
 * Provides static mapper instances so that we can
 * quickly just grab a mapper and do work without
 * having to call {@link ComponentMapper} to get an
 * instance all over the place.
 */
public class K2ComponentMappers {

  public static ComponentMapper<AnimationComponent> animation = ComponentMapper.getFor(AnimationComponent.class);
  public static ComponentMapper<BodyComponent> body = ComponentMapper.getFor(BodyComponent.class);
  public static ComponentMapper<BoundsComponent> bounds = ComponentMapper.getFor(BoundsComponent.class);
  public static ComponentMapper<CircleBoundsComponent> circleBounds = ComponentMapper
      .getFor(CircleBoundsComponent.class);
  public static ComponentMapper<ClickableComponent> clickable = ComponentMapper.getFor(ClickableComponent.class);
  public static ComponentMapper<CollisionComponent> collision = ComponentMapper.getFor(CollisionComponent.class);
  public static ComponentMapper<DamageComponent> damage = ComponentMapper.getFor(DamageComponent.class);
  public static ComponentMapper<EnemyComponent> enemy = ComponentMapper.getFor(EnemyComponent.class);
  public static ComponentMapper<FadingComponent> fading = ComponentMapper.getFor(FadingComponent.class);
  public static ComponentMapper<FPSComponent> fps = ComponentMapper.getFor(FPSComponent.class);
  public static ComponentMapper<FollowerComponent> follower = ComponentMapper.getFor(FollowerComponent.class);
  public static ComponentMapper<HealthComponent> health = ComponentMapper.getFor(HealthComponent.class);
  public static ComponentMapper<InactiveComponent> inactive = ComponentMapper.getFor(InactiveComponent.class);
  public static ComponentMapper<KinematicComponent> kinematic = ComponentMapper.getFor(KinematicComponent.class);
  public static ComponentMapper<MoveToComponent> moveTo = ComponentMapper.getFor(MoveToComponent.class);
  public static ComponentMapper<MultiBoundsComponent> multiBounds = ComponentMapper.getFor(MultiBoundsComponent.class);
  public static ComponentMapper<OscillationComponent> oscillation = ComponentMapper.getFor(OscillationComponent.class);
  public static ComponentMapper<ParticleComponent> particle = ComponentMapper.getFor(ParticleComponent.class);
  public static ComponentMapper<ParticleEmitterComponent> particleEmitter = ComponentMapper
      .getFor(ParticleEmitterComponent.class);
  public static ComponentMapper<PathFollowComponent> pathFollow = ComponentMapper.getFor(PathFollowComponent.class);
  public static ComponentMapper<PlayerComponent> player = ComponentMapper.getFor(PlayerComponent.class);
  public static ComponentMapper<RemainInBoundsComponent> remainInBounds = ComponentMapper
      .getFor(RemainInBoundsComponent.class);
  public static ComponentMapper<RemoveComponent> remove = ComponentMapper.getFor(RemoveComponent.class);
  public static ComponentMapper<RotationComponent> rotation = ComponentMapper.getFor(RotationComponent.class);
  public static ComponentMapper<ScreenWrapComponent> screenWrap = ComponentMapper.getFor(ScreenWrapComponent.class);
  public static ComponentMapper<ShakeComponent> shake = ComponentMapper.getFor(ShakeComponent.class);
  public static ComponentMapper<StateComponent> state = ComponentMapper.getFor(StateComponent.class);
  public static ComponentMapper<SteeringComponent> steering = ComponentMapper.getFor(SteeringComponent.class);
  public static ComponentMapper<TextComponent> text = ComponentMapper.getFor(TextComponent.class);
  public static ComponentMapper<TextureComponent> texture = ComponentMapper.getFor(TextureComponent.class);
  public static ComponentMapper<TransformComponent> transform = ComponentMapper.getFor(TransformComponent.class);
  public static ComponentMapper<TweenComponent> tween = ComponentMapper.getFor(TweenComponent.class);
  public static ComponentMapper<TypeComponent> type = ComponentMapper.getFor(TypeComponent.class);
  public static ComponentMapper<VelocityComponent> velocity = ComponentMapper.getFor(VelocityComponent.class);
}
