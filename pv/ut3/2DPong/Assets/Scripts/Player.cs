using System;
using UnityEngine;
using UnityEngine.InputSystem;

public class Player : MonoBehaviour
{

    public InputActionAsset InputActions;

    [SerializeField] private Rigidbody2D rb;

    private InputAction m_moveAction;

    private Vector2 m_move;

    public const float MovementSpeed = 5;

    private const String ACTION_MAP = "Player";
    private const String MOVE_ACTION = "Move";

    // Awake is called when the script instance is being loaded
    void Awake()
	{
        // assign the action movement actions
		m_moveAction = InputSystem.actions.FindAction(MOVE_ACTION);
	}

    // OnEnable is called when the object becomes enabled and active
	void OnEnable()
	{
        // enable the action map
        InputActions.FindActionMap(ACTION_MAP).Enable();
	}

    void FixedUpdate()
	{
        m_move = m_moveAction.ReadValue<Vector2>();
	}

    // Update is called once per frame
    void Update()
    {
        rb.MovePosition(rb.position + m_move * MovementSpeed * Time.fixedDeltaTime);
    }

    // Called when the behaviour becomes disabled or inactive
	void OnDisable()
	{
        // disable the action map
        InputActions.FindActionMap(ACTION_MAP).Disable();
	}
}
