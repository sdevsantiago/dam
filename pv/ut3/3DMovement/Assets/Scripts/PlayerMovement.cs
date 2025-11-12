using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerMovement : MonoBehaviour
{
    private readonly String HORIZONTAL = "Horizontal";
    private readonly String VERTICAL = "Vertical";
    private readonly String ROTATION = "Mouse X";
    private readonly String ELEVATION = "Mouse Y";

    public float baseMovementSpeed = 1.5f;
    public float rotationSpeed = 10f;
    public float jumpForce = 100f;

    private Rigidbody rb;

    // Start is called before the first frame update
    void Start()
    {
        // Get Rigidbody component
        rb = GetComponent<Rigidbody>();

        // Lock cursor to center of the screen (helps with camera movement)
        Cursor.lockState = CursorLockMode.Locked;
        // Hide cursor
        Cursor.visible = false;
    }

    // Update is called once per frame
    void Update()
    {
        // Get X and Z axis
        float horizontalMovement = Input.GetAxis(HORIZONTAL);
        float verticalMovement = Input.GetAxis(VERTICAL);
        // Get mouse
        float rotation = Input.GetAxis(ROTATION);
        float movementSpeed = Input.GetKeyDown(KeyCode.LeftShift) ? baseMovementSpeed * 2 : baseMovementSpeed;

        // Move player
        transform.Translate(new Vector3(horizontalMovement, 0, verticalMovement) * movementSpeed * Time.deltaTime);
        // Move camera
        transform.Rotate(new Vector3(0, rotation, 0) * rotationSpeed);

        // Jump
        if (Input.GetKeyDown(KeyCode.Space)) rb.AddForce(Vector3.up * jumpForce, ForceMode.Impulse);
    }
}
