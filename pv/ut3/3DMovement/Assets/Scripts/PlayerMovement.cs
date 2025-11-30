using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerMovement : MonoBehaviour
{
    private readonly String HORIZONTAL = "Horizontal";
    private readonly String VERTICAL = "Vertical";
    private readonly String ROTATION = "Mouse X";

    public float baseMovementSpeed = 3f;
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

    void FixedUpdate()
    {
        float horizontal = Input.GetAxis(HORIZONTAL);
        float vertical = Input.GetAxis(VERTICAL);

        Vector3 direction = transform.forward * vertical + transform.right * horizontal;
        rb.MovePosition(rb.position + direction * baseMovementSpeed * Time.fixedDeltaTime);
    }


    // Update is called once per frame
    void Update()
    {
        // Get mouse
        float rotation = Input.GetAxis(ROTATION);
        // Move camera
        transform.Rotate(new Vector3(0, rotation, 0) * rotationSpeed);

        // Jump
        if (Input.GetKeyDown(KeyCode.Space))
        {
            Debug.Log("SALTO!");
            rb.AddForce(Vector3.up * jumpForce, ForceMode.Impulse);
        }

    }
}
